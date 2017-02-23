package com.example.strategy;

/**
 * 学生票的具体折扣类，八折
 *
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class StudentDiscount extends Discount {
    @Override
    double calculate(double price) {
        return price * 0.8;
    }
}
