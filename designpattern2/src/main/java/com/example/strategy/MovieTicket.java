package com.example.strategy;

/**
 * 电影票环境类
 *
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class MovieTicket {

    private double price;
    private Discount discount;// 维持一个对抽象折扣类的引用

    /**
     * 设置具体的折扣类型
     * @param discount 具体折扣类型
     */
    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    /**
     * 调用折扣抽象类的方法获取折扣后的价格
     *
     * @return 折扣后的价格
     */
    public double getPrice() {
        return discount.calculate(price);
    }

    /**
     * 设置初始价格
     *
     * @param price 初始价格
     */
    public void setPrice(double price){
        this.price = price;
    }
}
