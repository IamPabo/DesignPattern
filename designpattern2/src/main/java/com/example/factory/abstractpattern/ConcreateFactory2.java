package com.example.factory.abstractpattern;

/**
 * ���幤�� 2
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class ConcreateFactory2 extends AbstractFactory {

    /**
     * ���� 2 ��Ʒ��� A ��Ʒ
     * @return
     */
    @Override
    public AbstractProductA createProductA() {
        return new ConcreateProductA2();
    }

    /**
     * ���� 2 ��Ʒ��� B ��Ʒ
     * @return
     */
    @Override
    public AbstractProductB createProductB() {
        return new ConcreateProductB2();
    }
}
