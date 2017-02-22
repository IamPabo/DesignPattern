package com.example.factory.simple;

/**
 * 简单工厂模式（静态工厂模式）
 * <p>
 * 工厂类，判断产品类型并实例化
 *
 * 缺点 ： 如果要增加新产品，需要修改工厂类业务逻辑，违反开闭原则（对扩展开放，对修改关闭）
 *        过于庞大，包含过多的 if...else...,不利于维护和测试
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class SimpleFactory {

    private static AbstractProduct mProduct;

    /**
     * 根据传入参数判断生成 A 产品，还是 B 产品
     * @param type 产品类型
     * @return Product 抽象类
     */
    public static AbstractProduct getProduct(String type) {
        if ("A".equals(type)) {
            mProduct = new ProductA();
        } else if ("B".equals(type)) {
            mProduct = new ProductB();
        }
        return mProduct;
    }
}
