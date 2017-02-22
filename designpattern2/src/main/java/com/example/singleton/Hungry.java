package com.example.singleton;

/**
 * 单例模式的饿汉模式，不管用不用此对象都需要提前实例化唯一实例，
 * 请求后返回唯一实例
 *
 * 优点  1. 无须考虑多线程访问问题，可以确保实例的唯一性；
 *      2. 从调用速度和反应时间角度来讲，由于单例对象一开始就得以创建，因此要优于懒汉式单例。
 * 缺点  但是无论系统在运行时是否需要使用该单例对象，由于在类加载时该对象就需要创建，
 *      因此从资源利用效率角度来讲，饿汉式单例不及懒汉式单例，而且在系统加载时由于
 *      需要创建饿汉式单例对象，加载时间可能会比较长。
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class Hungry {
    //静态常量
    private static final Hungry hungry = new Hungry();

    /**
     * 静态方法获取唯一实例
     * @return 唯一实例
     */
    public static Hungry getHungry() {
        return hungry;
    }
}
