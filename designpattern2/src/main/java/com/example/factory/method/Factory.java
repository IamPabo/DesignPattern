package com.example.factory.method;


/**
 * ���󹤳��࣬���ڶ��������Ʒ������Ҫʵ�ֵķ���
 *
 * ���󹤳�ģʽ
 *
 * ȱ�� �� 1. ����²�Ʒʱ����Ҫ�����Ӧ�Ĳ�Ʒ��͹����࣬��������
 *        2. Ϊ����չ�ԣ���Ҫ�������㣬�ͻ��˴����о�ʹ�ó���㶨��
 *           ����ϵͳ����Ⱥ�����Ѷȣ�������Ҫ�õ� DOM ���䣬������ʵ���Ѷ�
 * @auther MaxLiu
 * @time 2017/2/21
 */

public interface Factory {
    public Product factoryMethod();
}
