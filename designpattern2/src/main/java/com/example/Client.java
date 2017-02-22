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
        // 测试单例模式
//        for(int i = 0; i < 10; i++) {
//            new Thread(new Client()).start();
//        }
        // 测试简单工厂模式
//        // 产品 A
//        SimpleFactory.getProduct("A").methodSame();
//        SimpleFactory.getProduct("A").methodDiff();
//        // 产品 B
//        SimpleFactory.getProduct("B").methodSame();
//        SimpleFactory.getProduct("B").methodDiff();
        // 测试工厂方法模式

//        // 声明工厂
//        Factory factory;
//        // 生产 A
//        factory = new FactoryA();
//        factory.factoryMethod();
//        // 生产 B
//        factory = new FactoryB();
//        factory.factoryMethod();

//        // 抽象工厂模式
//        AbstractFactory factory;
//
//        // 1 产品族
//        factory = new ConcreateFacotry1();
//        // A 产品
//        factory.createProductA();
//        // B 产品
//        factory.createProductB();
//
//        // 2 产品族
//        factory = new ConcreateFactory2();
//        // A 产品
//        factory.createProductA();
//        // B 产品
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
