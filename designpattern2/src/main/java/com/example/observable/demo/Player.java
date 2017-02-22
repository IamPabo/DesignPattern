package com.example.observable.demo;

/**
 * ����۲���
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
        System.out.println("���ס��" + this.name + "�����㣡");
    }

    @Override
    public void beAttacked(AllyControlCenter acc) {
        System.out.println(this.name + "��������");
        acc.notifyObserver(name);
    }
}
