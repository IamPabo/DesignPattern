package com.example.factory.method;


/**
 * 抽象工厂类，用于定义各个产品工厂需要实现的方法
 *
 * 抽象工厂模式
 *
 * 缺点 ： 1. 添加新产品时，需要添加相应的产品类和工厂类，开销增大
 *        2. 为了拓展性，需要引入抽象层，客户端代码中均使用抽象层定义
 *           增加系统抽象度和理解难度，可能需要用到 DOM 反射，增加了实现难度
 * @auther MaxLiu
 * @time 2017/2/21
 */

public interface Factory {
    public Product factoryMethod();
}
