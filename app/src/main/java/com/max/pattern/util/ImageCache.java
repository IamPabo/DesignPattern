package com.max.pattern.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 图片的二级缓存工具类：
 * 1. LruCache 用于内存缓存
 * 2. DiskLruCache 用于存储设备缓存
 *
 * @auther MaxLiu
 * @time 2017/2/28
 */

public class ImageCache {
    private Context context;
    // 获取自己的文件夹 /data/data/包名/files 用于存储设备缓存
    private static final String FILE_PATH = getFilesDir();
    // /data/data/包名/cache 用于内存缓存
    private static final String CACHE_PATH = getCacheDir();
    private static final int maxMemory =
            (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static final int cacheSize = maxMemory / 8;
    private LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }
    };

    private LruCache<String, Bitmap> diskCache;

    public ImageCache(Context context) {
        this.context = context;
    }
}
