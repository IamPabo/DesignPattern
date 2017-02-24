package com.example.strategy.demo.sonicfly;

/**
 * 超音速飞行
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class SuperSonicFly extends SonicFlyType {
    @Override
    public void getType() {
        System.out.println("飞行类型 --> 超音速飞行");
    }
}
