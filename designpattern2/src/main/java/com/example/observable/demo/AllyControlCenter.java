package com.example.observable.demo;

import java.util.ArrayList;

/**
 * ս�ӿ��������ࣺĿ����
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public abstract class AllyControlCenter {

    protected String allyName;
    protected ArrayList<Observer> players = new ArrayList<>();

    public void setAllyName(String allyName) {
        this.allyName = allyName;
    }

    public String getAllyName() {
        return this.allyName;
    }

    /**
     * ע�᷽��
     */
    public void join(Observer observer){
        System.out.println(observer.getName() + " ���� " + this.allyName + " ս�ӣ�");
        players.add(observer);
    }

    /**
     * ע������
     */
    public void quit(Observer observer){
        System.out.println(observer.getName() + " �˳� " + this.allyName + " ս�ӣ�");
    }

    /**
     * ��������֪ͨ����
     */
    public abstract void notifyObserver(String name);
}
