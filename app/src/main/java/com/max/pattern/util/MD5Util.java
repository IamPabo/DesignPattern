package com.max.pattern.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Url 中可能存在一些特殊字符，影响正常使用，所以可以将 Url 进行 MD5 加密后
 * 作为 key 值缓存
 *
 * @auther MaxLiu
 * @time 2017/3/1
 */

public class MD5Util {

    /**
     * 将URL转换成key
     *
     * @param url 图片的URL
     * @return 转换后的 key
     */
    public static String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) { // 没有此算法异常
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    /**
     * 将Url的字节数组转换成哈希字符串
     *
     * @param bytes URL的字节数组
     * @return 哈希字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        // 创建 StringBuilder 对象
        StringBuilder sb = new StringBuilder();
        // 循环遍历 byte 数组元素
        for (byte aByte : bytes) {
            // 这里是用来掩盖掉 前面的 24位 因为 int 类型有 32 位，而 Byte 有 8 位
            // 而计算机中对负数的存储是用补码的形式，
            // 所以只需要取后八位就可以了，这里使用 & 运算实现
            String hex = Integer.toHexString(0xFF & aByte);
            // 这里是为了统一格式，长度为 1(也就是 正1) --> 01 而 负1 --> FF
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
