package com.example.factory.abstractpattern;

/**
 * 抽象工程模式
 *
 * 工厂方法模式通过引入工厂等级结构，解决了简单工厂模式中工厂类职责太重的问题，
 * 但由于工厂方法模式中的每个工厂只生产一类产品，可能会导致系统中存在大量的工厂类，
 * 势必会增加系统的开销。此时，我们可以考虑将一些相关的产品组成一个“产品族”，
 * 由同一个工厂来统一生产，这就是我们本文将要学习的抽象工厂模式的基本思想。
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public abstract class AbstractFactory {
    public abstract AbstractProductA createProductA(); //工厂方法一
    public abstract AbstractProductB createProductB(); //工厂方法二
}
