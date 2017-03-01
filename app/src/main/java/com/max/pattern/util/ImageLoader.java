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
    // 线程工厂,用于定义线程的一些相同属性（这里用来打印自增长线程 name）
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        // AtomicInteger(1) 表示设置初始 <==> int = 1;
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(@NonNull Runnable r) {
            // public final int get() : 获取当前的值
            // public final int getAndSet(int newValue) : 取当前的值，并设置新的值
            // public final int getAndIncrement() : 获取当前的值，并自增
            // public final int getAndDecrement() : 获取当前的值，并自减
            // public final int getAndAdd(int delta) : 获取当前的值，并加上预期的值
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };
    // 线程池
    private static final Executor THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    sThreadFactory
            );
    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache diskLruCache;
    private boolean mIsDiskLruCacheCreated = false;

    /**
     * 获取 ImageLoader 实例
     *
     * @param context 上下文
     * @return ImageLoader 实例
     */
    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
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
     * 如果读取不到就调用{@link #syncBitmap(String, int, int)}方法，当图片加载成功
     * 后再将图片，图片的地址以及需要绑定的imageView封装成一个{@link LoaderResult}对象，
     * 然后再通过{@link #mMainHandler}向主线程发送一条消息，这样就可以在imageView
     * 中设置图片了之所以通过 Handler 中转是因为子线程无法直接更新UI
     * <p>
     * asyncLoad 中用到了线程池和 Handler
     *
     * @param uri       图片uri
     * @param imageView 载体 ImageView 控件
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public void asyncLoad(final String uri, final ImageView imageView,
                          final int reqWidth, final int reqHeight) {
        // 把 uri 作为 tag 绑定到对应的 ImageView 上
        // 用来解决图片错乱问题
        imageView.setTag(TAG_KEY_URI, uri);
        // 从内存缓存获取 Bitmap
        Bitmap bitmap = getFromMemory(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        // 如果内存缓存没有，则调用线程池异步加载(多个Runnable实现多个同步加载) Bitmap
        Runnable bitmapTask = new Runnable() {
            @Override
            public void run() {
                // 调用同步加载
                Bitmap bitmap = syncBitmap(uri, reqWidth, reqHeight);
                // 通过 Handler 更新 UI
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result)
                            .sendToTarget();
                }
            }
        };
        // 线程池开启执行（多个线程同时执行）
        THREAD_POOL_EXECUTOR.execute(bitmapTask);
    }

    /**
     * 同步加载 （不压缩）
     *
     * @param uri uri
     * @return Bitmap对象
     */
    public Bitmap syncBitmap(String uri) {
        return syncBitmap(uri, 0, 0);
    }

    /**
     * 同步加载  （从内存缓存、磁盘缓存、网络）
     * 同步加载的设计步骤：
     * 先从内存缓存尝试加载图片，找不到就去磁盘缓存拿，磁盘缓存拿不到就去网络拿
     * 这个方法不能再线程执行，在主线程执行就抛异常（
     * 有一个检查当前线程的 Looper 是否为主线程的 Looper 的判断）
     *
     * @param uri       uri
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return Bitmap对象
     */
    public Bitmap syncBitmap(String uri, int reqWidth, int reqHeight) {
        // 从内存缓存获取
        Bitmap bitmap = getFromMemory(uri);
        if (bitmap != null) {
            Log.d(TAG, "MemoryCache --> { url : " + uri + " }");
            return bitmap;
        }

        // 从磁盘缓存获取
        bitmap = getFromDisk(uri, reqWidth, reqHeight);
        if (bitmap != null) {
            Log.d(TAG, "DiskCache --> { url : " + uri + " }");
            return bitmap;
        }
        // 从网络获取 I/O 流保存到磁盘缓存
        try {
            bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
            Log.d(TAG, "HTTP --> { url:" + uri + " }");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果磁盘缓存文件不存在，从网络直接加载出 Bitmap
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            Log.w(TAG, "Warn --> DiskLruCache is not created!!");
            bitmap = downloadBitmapFromUrl(uri);
        }
        return bitmap;
    }

    /**
     * 构造方法，初始化
     *
     * @param context 上下文
     */
    private ImageLoader(Context context) {
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
            // 直接从 输入流（读取） 获取Bitmap
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
     * 从网络下载并添加到磁盘缓存
     *
     * @param url       url
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return 返回 Bitmap 对象
     * @throws IOException 磁盘缓存的添加：添加需要通过Editor来完成，
     *                     利用commit和abort方法来提交和撤销操作
     */
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight)
            throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        if (diskLruCache == null) {
            return null;
        }

        String key = MD5Util.hashKeyFormUrl(url);
        // 根据 key 获取 Editor 对象
        DiskLruCache.Editor editor = diskLruCache.edit(key);
        if (editor != null) {
            // 用 editor 获取输出流
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_SIZE);
            if (downloadUrlToStream(url, outputStream)) {// 写入输出流成功
                editor.commit();// 提交
            } else { // 写入输出流失败
                editor.abort(); // 撤销
            }
            diskLruCache.flush();// 关闭
        }
        return getFromDisk(url, reqWidth, reqHeight);
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
            // HttpURLConnection 获取 缓冲输入流（BufferedInputStream）
            in = new BufferedInputStream(
                    urlConnection.getInputStream(),
                    IO_BUFFER_SIZE
            );
            // 传入的 输出流（Editor的输出流） 获取 缓冲输出流（BufferedOutputStream）
            out = new BufferedOutputStream(
                    outputStream,
                    IO_BUFFER_SIZE
            );
            int b;
            // 如果可以读取文件
            while ((b = in.read()) != -1) {
                // 文件流写入 缓冲输出流（BufferedOutputStream）
                out.write(b);
            }
            return true;// 写入成功
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
            // 用来保证图片不会错乱
            if (uri.equals(result.uri)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(TAG, "set image bitmap,but url has changed, ignored!");
            }
        }
    };
}
