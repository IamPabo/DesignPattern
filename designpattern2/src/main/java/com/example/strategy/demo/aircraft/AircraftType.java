package com.example.strategy.demo.aircraft;

import com.example.strategy.demo.sonicfly.SonicFlyType;
import com.example.strategy.demo.takeoff.TakeOffType;

/**
 * 飞机种类
 * 包括 ： 直升飞机  客机  歼击机  鹞式战斗机
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public abstract class AircraftType {

    protected SonicFlyType sonicFly;
    protected TakeOffType takeOff;
    /**
     * 获取飞机类型
     */
    public abstract void getType();

    /**
     * 获取起飞特征
     */
    public void getTakeoff(){
        takeOff.getType();
    }
    /**
     * 设置起飞特征
     */
    public abstract void setTakeoff();

    /**
     * 获取飞行特征
     */
    public void getSonicFly(){
        sonicFly.getType();
    }

    /**
     *设置飞行特征
     */
    public abstract void setSonicFly();
}
