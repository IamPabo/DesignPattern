package com.example.strategy;

/**
 * 策略模式的客户端
 *
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class StrategyClient {

    public static void main(String args[]){
        MovieTicket movieTicket = new MovieTicket();
        double originalPrice = 60.0;// 原始票价
        double currentPrice;// 折扣后的票价

        movieTicket.setPrice(originalPrice);
        System.out.println("原始价为：" + originalPrice + " 元");
        System.out.println("---------------------------------");

        Discount discount;
        discount = new StudentDiscount();
        movieTicket.setDiscount(discount);
        System.out.println("--------------学生票价----------------");
        System.out.println("学生票为：" + movieTicket.getPrice() + " 元");

        discount = new ChildrenDiscount();
        movieTicket.setDiscount(discount);
        System.out.println("--------------儿童票价----------------");
        System.out.println("儿童票为：" + movieTicket.getPrice() + " 元");

        discount = new VIPDiscount();
        movieTicket.setDiscount(discount);
        System.out.println("--------------VIP票价----------------");
        System.out.println("VIP票为：" + movieTicket.getPrice());
    }
}
