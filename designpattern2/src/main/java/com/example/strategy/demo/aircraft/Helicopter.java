package com.example.strategy.demo.aircraft;

import com.example.strategy.demo.sonicfly.SonicFlyType;
import com.example.strategy.demo.sonicfly.SubSonicFly;
import com.example.strategy.demo.takeoff.TakeOffType;
import com.example.strategy.demo.takeoff.VerticalTakeOff;

/**
 * 直升飞机
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class Helicopter extends AircraftType {

    public Helicopter(){
        setTakeoff();
        setSonicFly();
    }

    @Override
    public void getType() {
        System.out.println("===== 直升飞机 =====");
    }

    @Override
    public void setTakeoff() {
        this.takeOff = new VerticalTakeOff();
    }

    @Override
    public void setSonicFly() {
        this.sonicFly = new SubSonicFly();
    }
}
