package com.example.observable.demo;

/**
 * 观察类接口
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public interface Observer {
    public String getName();
    public void setName(String name);
    public void help(); //声明支援盟友方法
    public void beAttacked(AllyControlCenter acc); //声明遭受攻击方法
}
