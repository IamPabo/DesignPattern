package com.max.pattern.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.support.v4.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片的二级缓存工具类：
 * 1. LruCache 用于内存缓存
 * 2. DiskLruCache 用于存储设备缓存
 *
 * @auther MaxLiu
 * @time 2017/2/28
 */

public class ImageLoader {
    // 获取最大内存
    private static final int maxMemory = (int)
            (Runtime.getRuntime().maxMemory() / 1024);
    // 指定内存缓存大小 （可以自己指定，觉得合适就行）最大内存的 1 /4
    private static final int cacheSize = maxMemory / 4;
    // 指定磁盘缓存大小 50M
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    //    // 获取自己的文件夹 /data/data/包名/files 用于存储设备缓存
//    private static final String FILE_PATH = getFilesDir();
//    // /data/data/包名/cache 用于内存缓存
//    private static final String CACHE_PATH = getCacheDir();
    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache diskLruCache;
    private boolean mIsDiskLruCacheCreated = false;

    public ImageLoader(Context context) {
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        // 获取缓存文件
        File diskCacheDir = getDiskCacheDir(context, "DiskCache");
        // 文件不存在 直接创建
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        // 判断该分区下可提供的空间是否足够
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                diskLruCache = DiskLruCache.open(
                        diskCacheDir,// 磁盘缓存路径
                        1,// appVersion 正常设置为 1 即可
                        1,// 单个 key 对应几个 value 正常设置为1
                        DISK_CACHE_SIZE// 缓存的总大小
                );
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据文件名，创建 File 文件夹
     *
     * @param context  上下文
     * @param diskName 文件夹名称
     * @return 创建好的 File
     */
    private File getDiskCacheDir(Context context, String diskName) {
        String cachePath;
        // 如果sd卡存在并且没有被移除
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + diskName);
    }

    /**
     * 存入内存缓存
     *
     * @param key    key值
     * @param bitmap 对应的 bitmap
     */
    public void addToMemory(String key, Bitmap bitmap) {
        if (getFromMemory(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    /**
     * 从内存缓存中获取
     *
     * @param key key值
     * @return 对应的 bitmap
     */
    private Bitmap getFromMemory(String key) {
        return memoryCache.get(key);
    }

    /**
     * 添加到磁盘缓存,需要用到 MD5 哈希转换
     * {@link MD5Util#hashKeyFormUrl(String)}
     * 添加需要通过 Editor 来完成，利用 commit 和 abort 方法执行提交和撤销操作
     *
     * @param url url
     * @return bitmap
     */
    private Bitmap addToDisk(String url, int reqWidth, int reqHeight) {
        // 判断如果当前是在 UI 线程中,则抛出异常
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Unable to request network from UI thread!");
        }
        if (diskLruCache == null) {
            return null;
        }
        // 经过 MD5 转换为哈希
        String key = MD5Util.hashKeyFormUrl(url);
        try {
            // 根据 key 获取 Editor 对象
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            if (editor != null) {
                // 获取输出流
                OutputStream out = editor.newOutputStream(DISK_CACHE_SIZE);
                if (downloadUrlToStream(url, out)) {
                    editor.commit();// 提交
                } else {
                    editor.abort();// 撤销
                }
                diskLruCache.flush();// 关闭
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回缓存成功的 Bitmap 对象
        return getFromDisk(key, reqWidth, reqHeight);
    }

    /**
     * 从磁盘缓存中获取
     *
     * @param url       url
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return Bitmap对象
     */
    public Bitmap getFromDisk(String url, int reqWidth, int reqHeight) {
        // 判断如果当前是在 UI 线程中,则抛出异常
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Unable to load bitmap from UI thread!");
        }
        if (diskLruCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        // 经过 MD5 转换为哈希
        String key = MD5Util.hashKeyFormUrl(url);
        try {
            // 获取 Snapshot （快照） 可以看做保存了文件信息的对象
            DiskLruCache.Snapshot snapShot = diskLruCache.get(key);
            if (snapShot != null) {
                // 获取文件输入流（读取）
                FileInputStream fileIn = (FileInputStream) snapShot
                        .getInputStream(DISK_CACHE_SIZE);
                // 通过文件输入流获取文件描述
                FileDescriptor fileDes = fileIn.getFD();
                bitmap = ImageResizer.decodeBitmapFromFile(
                        fileDes,// 文件描述
                        reqWidth,// 目标宽度
                        reqHeight// 目标高度
                );
                // 加入内存缓存
                if (bitmap != null) {
                    addToMemory(key, bitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从 url 下载图片到 磁盘缓存文件
     * 把 url 中的图片保存到 输出流（OutputStream）
     *
     * @param urlString    url
     * @param outputStream 输出流（写入 OutputStream）
     * @return true or false 成功或失败
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            // 字符串转换为 URL 对象
            URL url = new URL(urlString);
            // 通过 URL 对象获取 HttpURLConnection
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(
                    urlConnection.getInputStream(),
                    IO_BUFFER_SIZE
            );
            out = new BufferedOutputStream(
                    outputStream,
                    IO_BUFFER_SIZE
            );
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();// 关闭 HttpURLConnection
            }
            try {
                if (in != null) {
                    in.close(); // 关闭 缓冲输入流 BufferedInputStream
                }
                if (out != null) {
                    out.close();// 关闭 缓冲输出流 BufferedOutputStream
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取该分区上可用的字节数
     * @param path 文件路径
     * @return
     */
    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return stats.getBlockSizeLong() * stats.getAvailableBlocksLong();
    }
}
