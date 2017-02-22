package com.example.singleton;

/**
 * ����ĵ���ģʽ��ͨ����̬�����;�̬����ʵ�֣��������Ϊnull��
 * ����ù��췽��ʵ�������أ�����ֱ�ӷ���
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class Normal {

    private static int count = 0;
    //˽�о�̬����
    private static Normal normal = null;

    private Normal() {

    }

    /**
     * ���о�̬����������Ψһʵ��
     *
     * @return Ψһʵ��
     */
    public static Normal getNormal() {
        if (normal == null) {
            normal = new Normal();
            count++;
        }
        System.out.println("�� " + count + " ��ʵ��");
        return normal;
    }
}
