package com.example.factory.method;

/**
 * A 产品工厂类，生产 A 产品
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class FactoryA implements Factory{
    @Override
    public Product factoryMethod() {
        return new ProductA();
    }
}
