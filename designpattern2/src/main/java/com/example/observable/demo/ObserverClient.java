package com.example.observable.demo;

/**
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class ObserverClient {

    public static void main(String arg[]){
        AllyControlCenter allyControlCenter
                = new ConcreteAllyControlCenter("�����޵�");
        Observer observer1 = new Player();
        observer1.setName("������");
        Observer observer2 = new Player();
        observer2.setName("����");
        Observer observer3 = new Player();
        observer3.setName("֩����");
        Observer observer4 = new Player();
        observer4.setName("������");
        Observer observer5 = new Player();
        observer5.setName("�̾���");
        allyControlCenter.join(observer1);
        allyControlCenter.join(observer2);
        allyControlCenter.join(observer3);
        allyControlCenter.join(observer4);
        allyControlCenter.join(observer5);
        observer4.beAttacked(allyControlCenter);
        allyControlCenter.quit(observer4);
    }
}
