package com.example.strategy.demo.aircraft;

import com.example.strategy.demo.sonicfly.SonicFlyType;
import com.example.strategy.demo.sonicfly.SuperSonicFly;
import com.example.strategy.demo.takeoff.LongDistanceTakeOff;
import com.example.strategy.demo.takeoff.TakeOffType;

/**
 * ¼ß»÷»ú
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class Fighter extends AircraftType {

    public Fighter(){
        setTakeoff();
        setSonicFly();
    }
    @Override
    public void getType() {
        System.out.println("===== ¼ß»÷»ú =====");
    }

    @Override
    public void setTakeoff() {
        this.takeOff = new LongDistanceTakeOff();
    }

    @Override
    public void setSonicFly() {
        this.sonicFly = new SuperSonicFly();
    }
}
