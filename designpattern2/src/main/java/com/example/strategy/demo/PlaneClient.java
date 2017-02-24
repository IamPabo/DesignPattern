package com.example.strategy.demo;

import com.example.strategy.demo.aircraft.AirPlane;
import com.example.strategy.demo.aircraft.AircraftType;
import com.example.strategy.demo.aircraft.Fighter;
import com.example.strategy.demo.aircraft.Harrier;
import com.example.strategy.demo.aircraft.Helicopter;

/**
 * ����ģʽ
 * �ɻ�����ģʽ�Ŀͻ���
 *
 * @auther MaxLiu
 * @time 2017/2/24
 */

public class PlaneClient {

    public static void main(String args[]){
        AircraftType aircraftType;

        // ֱ����
        aircraftType = new Helicopter();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
        // �ͻ�
        aircraftType = new AirPlane();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
        // �߻���
        aircraftType = new Fighter();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
        // ��ʽս����
        aircraftType = new Harrier();
        aircraftType.getType();
        aircraftType.getTakeoff();
        aircraftType.getSonicFly();
    }
}
