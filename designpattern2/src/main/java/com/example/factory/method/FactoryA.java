package com.example.factory.method;

/**
 * A ��Ʒ�����࣬���� A ��Ʒ
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
