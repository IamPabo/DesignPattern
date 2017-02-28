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

    /**
     * ð������
     * ð�������㷨���������£����Ӻ���ǰ��
     * 1.�Ƚ����ڵ�Ԫ�ء������һ���ȵڶ����󣬾ͽ�������������
     * 2.��ÿһ������Ԫ����ͬ���Ĺ������ӿ�ʼ��һ�Ե���β�����һ�ԡ�����һ�㣬����Ԫ��Ӧ�û�����������
     * 3.������е�Ԫ���ظ����ϵĲ��裬�������һ����
     * 4.����ÿ�ζ�Խ��Խ�ٵ�Ԫ���ظ�����Ĳ��裬ֱ��û���κ�һ��������Ҫ�Ƚϡ�
     *
     * @param a ����
     */


    /**
     * ���������㷨
     * ����˼�룺ѡ��һ����׼Ԫ��,ͨ��ѡ���һ��Ԫ�ػ������һ��Ԫ��,ͨ��һ��ɨ�裬
     * ���������зֳ�������,һ���ֱȻ�׼Ԫ��С,һ���ִ��ڵ��ڻ�׼Ԫ��,��ʱ��׼Ԫ��
     * �����ź�������ȷλ��,Ȼ������ͬ���ķ����ݹ�����򻮷ֵ������֡�
     *
     * @param arr   ���������
     * @param start ��ʼ������
     * @param end   ����������
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            // s�Ƿ���λ�ã������������е�����λ��
            int s = HoarePartition(arr, start, end);
            quickSort(arr, start, s - 1);
            quickSort(arr, s + 1, start);
        }
    }

    /**
     * �������һ��Ԫ��Ϊ���ᣬ����������л���
     * ���ط��ѵ��������������е�����λ��
     */
    private static int HoarePartition(int[] arr, int start, int end) {
        int p = arr[start];
        int i = start + 1;
        int j = end;
        if (i == j) {
            if (arr[j] >= p) {//��ʱ���ѵ��ҷ�ֻ��һ��Ԫ�أ��Ҵ��ڷ��ѵ㴦ֵ
                return start;
            }
        }
        while (i < j) {
            while (arr[i] < p) {
                if (i == end) {
                    break;
                }
                i += 1;
            }
            while (arr[j] >= p) {
                if (j == (start + 1)) {
                    if (arr[j] >= p) {  //��ʱΪ���ѵ��ҷ�ȫ���Ǵ��ڵ���p��Ԫ��
                        System.out.println(Arrays.toString(arr));
                        return start;
                    }
                    break;
                }
                j -= 1;
            }
            swapArray(arr, i, j);
        }
        swapArray(arr, i, j);    //��i>=j�������һ�ν���
        swapArray(arr, j, start);
        System.out.println(Arrays.toString(arr));
        return j;
    }

    /**
     * ����������������ͬλ���ϵ�Ԫ��ֵ
     */
    private static void swapArray(int[] arr, int m, int n) {
        int temp = arr[m];
        arr[m] = arr[n];
        arr[n] = temp;
    }

}
