package com.example.observable.demo2;

import java.util.Observable;

/**
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class MyPerson extends Observable {
    private int age;
    private String name;
    private String sax;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        setChanged();
        notifyObservers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public String getSax() {
        return sax;
    }

    public void setSax(String sax) {
        this.sax = sax;
    }

    @Override
    public String toString() {
        return "MyPerson [age=" + age + ", name=" + name + ", sax=" + sax + "]";
    }
}
