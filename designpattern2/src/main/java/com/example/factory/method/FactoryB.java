package com.example.factory.method;

/**
 * B 产品工厂类，用于生产 B 产品
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class FactoryB implements Factory {
    @Override
    public Product factoryMethod() {
        return new ProductB();
    }
}
