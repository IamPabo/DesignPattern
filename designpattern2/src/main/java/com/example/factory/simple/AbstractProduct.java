package com.example.factory.simple;

/**
 * �����࣬��������������
 *
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public abstract class AbstractProduct {

    /**
     * ��ͬ��ִ��
     */
    public void methodSame(){
        System.out.println("���ǲ�Ʒ�Ĺ�������");
    }

    /**
     * ��ͬ��ִ��
     */
    public abstract void methodDiff();
}
