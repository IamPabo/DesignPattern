package com.example.factory.method;

import com.example.factory.simple.AbstractProduct;

/**
 * A 产品类
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class ProductA implements Product {

    @Override
    public void produce() {
        System.out.println("生产A产品");
    }
}
