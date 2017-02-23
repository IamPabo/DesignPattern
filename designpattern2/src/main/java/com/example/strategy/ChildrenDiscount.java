package com.example.strategy;

/**
 * 儿童票的具体折扣类，优惠 10 元
 *
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class ChildrenDiscount extends Discount {
    @Override
    double calculate(double price) {
        return price - 10;
    }
}
