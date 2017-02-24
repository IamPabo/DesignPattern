package com.example.strategy.demo.takeoff;

/**
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class VerticalTakeOff extends TakeOffType {
    @Override
    public void getType() {
        System.out.println("飞行类型 --> 垂直起飞");
    }
}
