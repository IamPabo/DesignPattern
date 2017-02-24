package com.example.strategy.demo;

import com.example.strategy.demo.aircraft.AirPlane;
import com.example.strategy.demo.aircraft.AircraftType;
import com.example.strategy.demo.aircraft.Fighter;
import com.example.strategy.demo.aircraft.Harrier;
import com.example.strategy.demo.aircraft.Helicopter;

/**
 * 策略模式
 * 飞机飞行模式的客户端
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class PlaneClient {

    public static void main(String args[]){
        AircraftType aircraftType;

        // 直升机
        aircraftType = new Helicopter();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
        // 客机
        aircraftType = new AirPlane();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
        // 歼击机
        aircraftType = new Fighter();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
        // 鹞式战斗机
        aircraftType = new Harrier();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
    }
}
