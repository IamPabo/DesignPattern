package com.example.strategy;

/**
 * 折扣算法抽象类
 *
 * @auther MaxLiu
 * @time 2017/2/23
 */

abstract class Discount {

    /**
     * 折扣方法
     * @param price 传入原价格
     * @return 返回折扣后的价格
     */
    abstract double calculate(double price);
}
