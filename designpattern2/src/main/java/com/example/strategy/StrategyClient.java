package com.example.strategy;

/**
 * ����ģʽ�Ŀͻ���
 *
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class StrategyClient {

    public static void main(String args[]){
        MovieTicket movieTicket = new MovieTicket();
        double originalPrice = 60.0;// ԭʼƱ��
        double currentPrice;// �ۿۺ��Ʊ��

        movieTicket.setPrice(originalPrice);
        System.out.println("ԭʼ��Ϊ��" + originalPrice + " Ԫ");
        System.out.println("---------------------------------");

        Discount discount;
        discount = new StudentDiscount();
        movieTicket.setDiscount(discount);
        System.out.println("--------------ѧ��Ʊ��----------------");
        System.out.println("ѧ��ƱΪ��" + movieTicket.getPrice() + " Ԫ");

        discount = new ChildrenDiscount();
        movieTicket.setDiscount(discount);
        System.out.println("--------------��ͯƱ��----------------");
        System.out.println("��ͯƱΪ��" + movieTicket.getPrice() + " Ԫ");

        discount = new VIPDiscount();
        movieTicket.setDiscount(discount);
        System.out.println("--------------VIPƱ��----------------");
        System.out.println("VIPƱΪ��" + movieTicket.getPrice());
    }
}
