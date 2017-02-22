package com.example.singleton;

/**
 * 单例模式中的懒汉模式，在调用时才会实例化，类加载时并不会实例化，这种技术
 * 又称作延时加载技术，即需要的时候在加载实例，为了避免多个线程同时调用 get
 * 实例的方法，使用关键字 synchronized 实现。
 *
 * 优点  在第一次使用时创建，无须一直占用系统资源，实现了延迟加载，
 *
 * 缺点  但是必须处理好多个线程同时访问的问题，特别是当单例类作为资源控制器，
 *      在实例化时必然涉及资源初始化，而资源初始化很有可能耗费大量时间，
 *      这意味着出现多线程同时首次引用此类的机率变得较大，
 *      需要通过双重检查锁定等机制进行控制，这将导致系统性能受到一定影响。
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class Lazy {
    private static Lazy lazy;
    private static int count;

    private Lazy(){
        System.out.println("创建实例");
    }

    /**
     * 方发锁，每次调用该方法都需要进行线程锁定判断，在多线程并发情况下，会导致性能大大降低。
     * 所以需要对懒汉模式进行改进{@link #getLazyImprove()}
     *
     * @return lazy实例
     */
    public synchronized static Lazy getLazy() {
        if(lazy == null){
            lazy = new Lazy();
        }
        return lazy;
    }

    /**
     * 懒汉模式的改进版，把方法锁改为块锁 synchronized，貌似问题已经解决，，但是还是会存在
     * 单例的不唯一。
     * 原因 ： 如果有线程A和线程B同时进入 getLazyImprove 方法，并且都判断 lazy == null
     * A先拿到锁，实例化lazy，返回后 B 拿到锁，实例化lazy，返回。此时会出现两个甚至多个实例，
     * 无法保证唯一实例。{@link #getLazyFinalChapter()}
     *
     * @return lazy实例
     */
    public static Lazy getLazyImprove(){
        if(lazy == null){
            synchronized (Lazy.class){
                lazy = new Lazy();
            }
        }
        return lazy;
    }

    /**
     * 使用双重锁机制，此时线程A拿到锁返回 lazy 实例，线程B拿到锁，先判断是否存在 lazy
     * 而此时 lazy 已经实例化（线程A实例化返回），所以线程B不执行实例化，实现了唯一实例。
     *
     * @return lazy实例
     */
    public static Lazy getLazyFinalChapter(){
        if(lazy == null){
            synchronized (Lazy.class){
                if(lazy == null) {
                    lazy = new Lazy();
                }
            }
        }
        return lazy;
    }
}
