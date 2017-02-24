package com.example.strategy.demo.aircraft;

import com.example.strategy.demo.sonicfly.SonicFlyType;
import com.example.strategy.demo.sonicfly.SubSonicFly;
import com.example.strategy.demo.takeoff.LongDistanceTakeOff;
import com.example.strategy.demo.takeoff.TakeOffType;

/**
 * 人字
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class AirPlane extends AircraftType {

    public AirPlane() {
        setTakeoff();
        setSonicFly();
    }

    @Override
    public void getType() {
        System.out.println("===== 人字 =====");
    }

    @Override
    public void setTakeoff() {
        this.takeOff = new LongDistanceTakeOff();
    }

    @Override
    public void setSonicFly() {
        this.sonicFly = new SubSonicFly();
    }
}
