package com.example.observable.demo;

/**
 * 具体战队控制中心
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class ConcreteAllyControlCenter extends AllyControlCenter {

    public ConcreteAllyControlCenter(String allyName) {
        System.out.println(allyName + "战队组建成功！");
        System.out.println("----------------------------");
        this.allyName = allyName;
    }

    @Override
    public void notifyObserver(String name) {
        System.out.println(allyName + " 战队紧急通知,队员 " + name
                + " 遭受敌人攻击！请求支援");
        // 通知所有队员（除了自己），请求支援
        for(Object observer : players){
            if(!((Observer)observer).getName().equalsIgnoreCase(name)){
                ((Observer)observer).help();
            }
        }
    }
}
