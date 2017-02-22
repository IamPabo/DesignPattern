package com.example.observable.demo;

/**
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class ObserverClient {

    public static void main(String arg[]){
        AllyControlCenter allyControlCenter
                = new ConcreteAllyControlCenter("ÓîÖæÎŞµĞ");
        Observer observer1 = new Player();
        observer1.setName("°ÂÌØÂü");
        Observer observer2 = new Player();
        observer2.setName("³¬ÈË");
        Observer observer3 = new Player();
        observer3.setName("Ö©ÖëÏÀ");
        Observer observer4 = new Player();
        observer4.setName("ÕÅÈı·á");
        Observer observer5 = new Player();
        observer5.setName("ÂÌ¾ŞÈË");
        allyControlCenter.join(observer1);
        allyControlCenter.join(observer2);
        allyControlCenter.join(observer3);
        allyControlCenter.join(observer4);
        allyControlCenter.join(observer5);
        observer4.beAttacked(allyControlCenter);
        allyControlCenter.quit(observer4);
    }
}
