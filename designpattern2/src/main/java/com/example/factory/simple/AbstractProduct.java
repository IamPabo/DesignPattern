package com.example.factory.simple;

/**
 * 抽象类，供工厂方法调用
 *
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public abstract class AbstractProduct {

    /**
     * 相同的执行
     */
    public void methodSame(){
        System.out.println("我是产品的公共方法");
    }

    /**
     * 不同的执行
     */
    public abstract void methodDiff();
}
