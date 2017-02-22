package com.example.observable.demo;

/**
 * 具体观察者
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class Player implements Observer {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void help() {
        System.out.println("坚持住，" + this.name + "来救你！");
    }

    @Override
    public void beAttacked(AllyControlCenter acc) {
        System.out.println(this.name + "被攻击！");
        acc.notifyObserver(name);
    }
}
