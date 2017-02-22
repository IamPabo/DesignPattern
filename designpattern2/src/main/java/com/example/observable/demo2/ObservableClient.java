package com.example.observable.demo2;

/**
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class ObservableClient {

    public static void main(String args[]){
        MyPerson person = new MyPerson();
        MyObserver observer1 = new MyObserver(1);
        MyObserver observer2 = new MyObserver(2);
        MyObserver observer3 = new MyObserver(3);
        observer1.setMyPerson(person);
        observer2.setMyPerson(person);
        observer3.setMyPerson(person);
        person.setName("我是一个学生");
        person.setAge(15);
        person.setSax("男");
        observer1.update(person,person.getName());
        observer1.update(person,person.getAge());
        observer2.update(person,person.getName());
        observer2.update(person,person.getAge());
        observer3.update(person,person.getName());
        observer3.update(person,person.getAge());

    }
}
