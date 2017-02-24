package com.example.strategy.demo.aircraft;

import com.example.strategy.demo.sonicfly.SonicFlyType;
import com.example.strategy.demo.sonicfly.SuperSonicFly;
import com.example.strategy.demo.takeoff.TakeOffType;
import com.example.strategy.demo.takeoff.VerticalTakeOff;

/**
 * 鹞式战斗机
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class Harrier extends AircraftType {

    public Harrier(){
        setTakeoff();
        setSonicFly();
    }

    @Override
    public void getType() {
        System.out.println("===== 鹞式战斗机 =====");
    }

    @Override
    public void setTakeoff() {
        this.takeOff = new VerticalTakeOff();
    }

    @Override
    public void setSonicFly() {
        this.sonicFly = new SuperSonicFly();
    }
}
