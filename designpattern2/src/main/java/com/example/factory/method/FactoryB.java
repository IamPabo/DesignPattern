package com.example.factory.method;

/**
 * B ��Ʒ�����࣬�������� B ��Ʒ
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
