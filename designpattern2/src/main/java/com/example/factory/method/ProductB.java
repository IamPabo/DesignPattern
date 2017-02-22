package com.example.factory.method;

/**
 * B 产品类
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class ProductB implements Product {
    @Override
    public void produce() {
        System.out.println("生产B产品");
    }
}
