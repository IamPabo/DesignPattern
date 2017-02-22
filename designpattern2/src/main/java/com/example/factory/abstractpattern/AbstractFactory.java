package com.example.factory.abstractpattern;

/**
 * ���󹤳�ģʽ
 *
 * ��������ģʽͨ�����빤���ȼ��ṹ������˼򵥹���ģʽ�й�����ְ��̫�ص����⣬
 * �����ڹ�������ģʽ�е�ÿ������ֻ����һ���Ʒ�����ܻᵼ��ϵͳ�д��ڴ����Ĺ����࣬
 * �Ʊػ�����ϵͳ�Ŀ�������ʱ�����ǿ��Կ��ǽ�һЩ��صĲ�Ʒ���һ������Ʒ�塱��
 * ��ͬһ��������ͳһ��������������Ǳ��Ľ�Ҫѧϰ�ĳ��󹤳�ģʽ�Ļ���˼�롣
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public abstract class AbstractFactory {
    public abstract AbstractProductA createProductA(); //��������һ
    public abstract AbstractProductB createProductB(); //����������
}
