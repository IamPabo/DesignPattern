package com.example.factory.method;

import com.example.factory.simple.AbstractProduct;

/**
 * A ��Ʒ��
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class ProductA implements Product {

    @Override
    public void produce() {
        System.out.println("����A��Ʒ");
    }
}
