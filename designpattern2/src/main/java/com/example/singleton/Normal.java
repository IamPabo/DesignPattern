package com.example.singleton;

/**
 * 最初的单例模式，通过静态变量和静态方法实现，如果变量为null，
 * 则调用构造方法实例化返回，否则直接返回
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class Normal {

    private static int count = 0;
    //私有静态变量
    private static Normal normal = null;

    private Normal() {

    }

    /**
     * 公有静态方法，返回唯一实例
     *
     * @return 唯一实例
     */
    public static Normal getNormal() {
        if (normal == null) {
            normal = new Normal();
            count++;
        }
        System.out.println("第 " + count + " 个实例");
        return normal;
    }
}
