package com.example.javatest;

/**
 * == 比较的是对象的引用， equals 默认行为是比较引用
 *
 * @auther MaxLiu
 * @time 2017/3/3
 */

public class DogClient {
    public static void main(String args[]){
        Dog dog1 = new Dog("spot","Ruff!");
        Dog dog2 = new Dog("scruffy","Wurf!");
        Dog dog3 = dog1;
        dog1.name = dog2.name = "spot";

        System.out.println("name : " + dog1.name + "  says : " + dog1.says);
        System.out.println("name : " + dog2.name + "  says : " + dog2.says);

        System.out.println(dog1 == dog2);// false
        System.out.println(dog1.equals(dog2));// false
        System.out.println(dog1 == dog3);// true
        System.out.println(dog1.equals(dog3));// true
    }
}
