package com.example.strategy.demo.takeoff;

/**
 * 长距离起飞
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class LongDistanceTakeOff extends TakeOffType {
    @Override
    public void getType() {
        System.out.println("起飞类型 --> 长距离起飞");
    }
}
