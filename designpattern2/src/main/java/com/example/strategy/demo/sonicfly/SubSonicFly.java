package com.example.strategy.demo.sonicfly;

/**
 * 亚音速飞行
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class SubSonicFly extends SonicFlyType {
    @Override
    public void getType() {
        System.out.println("飞行类型 --> 亚音速飞行");
    }
}
