package com.example.factory.abstractpattern;

/**
 * 具体工厂 2
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class ConcreateFactory2 extends AbstractFactory {

    /**
     * 生产 2 产品族的 A 产品
     * @return
     */
    @Override
    public AbstractProductA createProductA() {
        return new ConcreateProductA2();
    }

    /**
     * 生产 2 产品族的 B 产品
     * @return
     */
    @Override
    public AbstractProductB createProductB() {
        return new ConcreateProductB2();
    }
}
