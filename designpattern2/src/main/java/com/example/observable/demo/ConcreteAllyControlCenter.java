package com.example.observable.demo;

/**
 * ����ս�ӿ�������
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class ConcreteAllyControlCenter extends AllyControlCenter {

    public ConcreteAllyControlCenter(String allyName) {
        System.out.println(allyName + "ս���齨�ɹ���");
        System.out.println("----------------------------");
        this.allyName = allyName;
    }

    @Override
    public void notifyObserver(String name) {
        System.out.println(allyName + " ս�ӽ���֪ͨ,��Ա " + name
                + " ���ܵ��˹���������֧Ԯ");
        // ֪ͨ���ж�Ա�������Լ���������֧Ԯ
        for(Object observer : players){
            if(!((Observer)observer).getName().equalsIgnoreCase(name)){
                ((Observer)observer).help();
            }
        }
    }
}
