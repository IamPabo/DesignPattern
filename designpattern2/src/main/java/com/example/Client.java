package com.example;

import com.example.factory.abstractpattern.AbstractFactory;
import com.example.factory.abstractpattern.ConcreateFacotry1;
import com.example.factory.abstractpattern.ConcreateFactory2;
import com.example.factory.method.Factory;
import com.example.factory.method.FactoryA;
import com.example.factory.method.FactoryB;
import com.example.singleton.Lazy;
import com.example.singleton.Normal;

public class Client implements Runnable {


    public static void main(String[] args) {
        // ���Ե���ģʽ
//        for(int i = 0; i < 10; i++) {
//            new Thread(new Client()).start();
//        }
        // ���Լ򵥹���ģʽ
//        // ��Ʒ A
//        SimpleFactory.getProduct("A").methodSame();
//        SimpleFactory.getProduct("A").methodDiff();
//        // ��Ʒ B
//        SimpleFactory.getProduct("B").methodSame();
//        SimpleFactory.getProduct("B").methodDiff();
        // ���Թ�������ģʽ

//        // ��������
//        Factory factory;
//        // ���� A
//        factory = new FactoryA();
//        factory.factoryMethod();
//        // ���� B
//        factory = new FactoryB();
//        factory.factoryMethod();

//        // ���󹤳�ģʽ
//        AbstractFactory factory;
//
//        // 1 ��Ʒ��
//        factory = new ConcreateFacotry1();
//        // A ��Ʒ
//        factory.createProductA();
//        // B ��Ʒ
//        factory.createProductB();
//
//        // 2 ��Ʒ��
//        factory = new ConcreateFactory2();
//        // A ��Ʒ
//        factory.createProductA();
//        // B ��Ʒ
//        factory.createProductB();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //Normal.getNormal();
            //Lazy.getLazyImprove();
            //Lazy.getLazyFinalChapter();
        }
    }
}
