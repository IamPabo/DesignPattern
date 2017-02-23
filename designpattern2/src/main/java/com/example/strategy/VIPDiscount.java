package com.example.strategy;

/**
 * VIP会员票的具体折扣类，半折
 *
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class VIPDiscount extends Discount {
    @Override
    double calculate(double price) {
        return price * 0.5;
    }
}
