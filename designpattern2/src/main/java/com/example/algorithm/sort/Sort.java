package com.example.algorithm.sort;

import java.util.Arrays;

/**
 * ð������
 * ����˼�� �� ����˼�룺��Ҫ�����һ�����У��Ե�ǰ��δ�ź���ķ�Χ�ڵ�ȫ������
 * ���϶��¶����ڵ����������ν��бȽϺ͵������ýϴ�������³�����С������ð��
 * ����ÿ�������ڵ����ȽϺ������ǵ�����������Ҫ���෴ʱ���ͽ����ǻ�����
 *
 * @auther MaxLiu
 * @time 2017/2/27
 */

public class Sort {
    public static final int MAX_TO_MIN = 0;
    public static final int MIN_TO_MAX = 1;

    public static void main(String args[]) {
        // �����õķ���
        int a[] = {1, 54, 6, 3, 78, 34, 12, 45};
        // ��ѡ������
        SelectSort.selectSort(a);
        //BubbleSort.bubbleSort(a);// ð������
        //quickSort(a, 0, a.length - 1);// ��������
    }
}
