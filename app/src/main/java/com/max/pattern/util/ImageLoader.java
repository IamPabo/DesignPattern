package com.max.pattern.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.max.pattern.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 图片的二级缓存工具类：
 * 1. LruCache 用于内存缓存
 * 2. DiskLruCache 用于存储设备缓存
 *
 * @auther MaxLiu
 * @time 2017/2/28
 */

public class ImageLoader {
    private static final String TAG = "ImageLoader";
    // 获取最大内存
    private static final int maxMemory = (int)
            (Runtime.getRuntime().maxMemory() / 1024);
    // 指定内存缓存大小 （可以自己指定，觉得合适就行）最大内存的 1 /4
    private static final int cacheSize = maxMemory / 4;
    // 指定磁盘缓存大小 50M
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    // ImageView 的 tag key
    private static final int TAG_KEY_URI = 0xff01;
    private static final int MESSAGE_POST_RESULT = 0xff11;
    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    // 线程工厂
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };
    // 线程池
    public static final Executor THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(
                    CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    sThreadFactory
            );
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
     * 异步加载(默认调用)
     *
     * @param uri       图片 uri
     * @param imageView 载体 ImageView 控件
     */
    public void asyncLoad(final String uri, final ImageView imageView) {
        asyncLoad(uri, imageView, 0, 0);
    }

    /**
     * 异步加载(可压缩调用)
     * 实现过程：
     * 首先先去内存缓存读取图片
     * 如果读取到就直接返回结果
     * <p>
     * 如果读取不到就调用loadBitmap方法，当图片加载成功后再将图片，
     * 图片的地址以及需要绑定的imageView封装成一个LoaderResult对象，
     * 然后再通过mMainHandler向主线程发送一条消息，这样就可以在imageView中设置图片了
     * 之所以通过Handler中转是因为子线程无法直接更新UI
     * <p>
     * bindBitmap中用到了线程池和Handler
     *
     * @param uri       图片uri
     * @param imageView 载体 ImageView 控件
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public void asyncLoad(final String uri, final ImageView imageView,
                          final int reqWidth, final int reqHeight) {
        // 把 uri 作为 tag 绑定到对应的 ImageView 上
        imageView.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = getFromMemory(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable bitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result)
                            .sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(bitmapTask);
    }

    /**
     * 同步加载  （从内存缓存、磁盘缓存、网络）
     * 同步加载的设计步骤：
     * 先从内存缓存尝试加载图片，找不到就去磁盘缓存拿，磁盘缓存拿不到就去网络拿
     * 这个方法不能再线程执行，在主线程执行就抛异常（
     * 有一个检查当前线程的Looper是否为主线程的Looper的判断）
     *
     * @param uri       uri
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return Bitmap对象
     */
    private Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = getFromMemory(uri);
        if (bitmap != null) {
            Log.d(TAG, "MemoryCache --> { url : " + uri + " }");
            return bitmap;
        }
        bitmap = getFromDisk(uri, reqWidth, reqHeight);
        if (bitmap != null) {
            Log.d(TAG, "DiskCache --> { url : " + uri + " }");
            return bitmap;
        }
        bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
        Log.d(TAG, "HTTP --> { url:" + uri + " }");
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            Log.w(TAG, "Warn --> DiskLruCache is not created!!");
            bitmap = downloadBitmapFromUrl(uri);
        }
        return bitmap;
    }

    /**
     * 从 uri 中加载 Bitmap
     *
     * @param uri uri
     * @return Bitmap 对象
     */
    private Bitmap downloadBitmapFromUrl(String uri) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(
                    urlConnection.getInputStream(),
                    IO_BUFFER_SIZE
            );
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String uri, int reqWidth, int reqHeight) {
        return null;
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
    private void addToMemory(String key, Bitmap bitmap) {
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
    private Bitmap getFromDisk(String url, int reqWidth, int reqHeight) {
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
     *
     * @param path 文件路径
     * @return 可用大小
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

    /**
     * 静态内部类，用于存储 Loader 信息
     */
    private static class LoaderResult {
        ImageView imageView;
        String uri;
        Bitmap bitmap;

        LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if (uri.equals(result.uri)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(TAG, "set image bitmap,but url has changed, ignored!");
            }
        }

        ;
    };
}
