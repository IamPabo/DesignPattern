package com.example.observable.demo2;

import java.util.Observable;
import java.util.Observer;

/**
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class MyObserver implements Observer{
    private int i;
    private MyPerson myPerson;//???????

    public MyObserver(int i){
        System.out.println("我是观察者---->" + i);
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public MyPerson getMyPerson() {
        return myPerson;
    }

    public void setMyPerson(MyPerson myPerson) {
        this.myPerson = myPerson;
    }

    @Override
    public void update(Observable observable, Object data) {
        System.out.println("观察者---->"+ i +"得到更新！");
        this.myPerson = (MyPerson)observable;
        System.out.println(observable.toString());
    }

}
