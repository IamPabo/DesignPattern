package com.max.pattern.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;
import java.io.InputStream;

/**
 * 加载大图Bitmap
 * 防止 OOM ,压缩 + 二级缓存策略
 *
 * @auther MaxLiu
 * @time 2017/2/28
 */

public class ImageResizer {

    /**
     * 从文件加载图片
     *
     * @param fd 文件描述，当 FileDescriptor 表示某文件时，我们可以通俗的将
     *           FileDescriptor 看成是该文件。但是，我们不能直接通过
     *           FileDescriptor 对该文件进行操作；若需要通过 FileDescriptor
     *           对该文件进行操作，则需要新创建 FileDescriptor 对应的 FileOutputStream
     *           ，再对文件进行操作。
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return 压缩后的 Bitmap 对象
     */
    public static Bitmap decodeBitmapFromFile(FileDescriptor fd,
                                              int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 第二个参数 Rect 只对 nine-patch（也就是 .9 图）有效
        // 规定 padding box 的值
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 加载从网络获取的图片
     *
     * @param in        请求网路的输入流 InputStream
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return 压缩后的 Bitmap 对象
     */
    public static Bitmap decodeBitmapFromUrl(InputStream in,
                                             int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 第二个参数 Rect 只对 nine-patch（也就是 .9 图）有效
        BitmapFactory.decodeStream(in, null, options);
        options.inSampleSize = calculateSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(in, null, options);
    }

    /**
     * 从资源文件读取图片
     *
     * @param resources 资源
     * @param resId     资源id
     * @param reqWidth  目标宽度
     * @param reqHeigth 目标高度
     * @return bitmap实例
     */
    public static Bitmap decodeBitmapFromResource(Resources resources, int resId,
                                                  int reqWidth, int reqHeigth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 该属性设置为 true 表示先不加载图片
        options.inJustDecodeBounds = true;
        // 调用该方法 options 就会包含所有的图片属性，包括我们需要的图片实际宽高
        BitmapFactory.decodeResource(resources, resId, options);
        // 通过图片属性和需要的宽高计算合适的宽高
        // inSampleSize = 1 原始大小  2 宽高为原来的一半  4 宽高为原来的四分之一 以此类推
        // 这里只会取 1 和 2 的倍数，如果取 3 会向下取距离它最近的 2 的倍数  所以会取 2
        options.inSampleSize = calculateSize(options, reqWidth, reqHeigth);
        // 该属性设置为 false 表示加载图片
        options.inJustDecodeBounds = false;
        // 返回设置了 options 的 Bitmap 对象
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 用于根据目标的宽高计算最合适的图片压缩后的宽高
     *
     * @param options   Options 对象
     * @param reqWidth  目标宽度
     * @param reqHeigth 目标高度
     * @return options.inSampleSize
     */
    private static int calculateSize(BitmapFactory.Options options, int reqWidth, int reqHeigth) {
        // 如果 需要的宽高为0，则返回 1 表示保持原始大小
        if (reqHeigth == 0 || reqWidth == 0) {
            return 1;
        }
        // 获取图片的宽高
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeigth || width > reqWidth) {
            // 获取图片宽高和需要宽高的比例
            // 因为 inSampleSize 取的不是 2 的倍数的话，系统会往下取最借鉴的 2 的倍数
            // 所以不用担心取值问题
            final int widthProportion = width / reqWidth;
            final int heightProportion = height / reqHeigth;
            // 取两个比例中最小的值,这样可以保证图片的的最终宽高大于容器的宽高，
            // 避免出现显示无法充满的问题
            inSampleSize = Math.min(widthProportion, heightProportion);
        }
        return inSampleSize;
    }
}
