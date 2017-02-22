package com.example.singleton;

/**
 * 一种更好的单例模式
 *
 * IoDH模式（Initialization Demand Holder）
 * 在IoDH中，我们在单例类中增加一个静态(static)内部类，在该内部类中创建单例对象，
 * 再将该单例对象通过getInstance()方法返回给外部使用
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class Better {

    private Better(){}

    /**
     * 使用静态内部类，在静态内部类中声明私有静态常量 Better,并实例化，这样可以通过
     * 控制改静态类的加载来控制单例的创建，并且不用通过锁机制来实现，性能不受影响。
     */
    private static class HolderClass{
        private static final Better better = new Better();
    }

    /**
     * 获取单例的方法，调用静态内部类的静态常量实现
     * @return 唯一实例 better
     */
    public static Better getInstance() {
        return HolderClass.better;
    }
}
