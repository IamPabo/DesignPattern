package com.example.factory.abstractpattern;

/**
 * ���幤�� 1
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class ConcreateFacotry1 extends AbstractFactory {

    /**
     * ���� 1 ��Ʒ��� A ��Ʒ
     * @return
     */
    @Override
    public AbstractProductA createProductA() {
        return new ConcreateProductA1();
    }

    /**
     * ���� 1 ��Ʒ��� B ��Ʒ
     * @return
     */
    @Override
    public AbstractProductB createProductB() {
        return new ConcreateProductB1();
    }
}
