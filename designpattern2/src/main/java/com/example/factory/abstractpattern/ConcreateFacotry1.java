package com.example.factory.abstractpattern;

/**
 * 具体工厂 1
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class ConcreateFacotry1 extends AbstractFactory {

    /**
     * 生产 1 产品族的 A 产品
     * @return
     */
    @Override
    public AbstractProductA createProductA() {
        return new ConcreateProductA1();
    }

    /**
     * 生产 1 产品族的 B 产品
     * @return
     */
    @Override
    public AbstractProductB createProductB() {
        return new ConcreateProductB1();
    }
}
