package com.example.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 基本思想 ： 基本思想：在要排序的一组数中，对当前还未排好序的范围内的全部数，
 * 自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，较小的往上冒。
 * 即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
 *
 * @auther MaxLiu
 * @time 2017/2/27
 */

public class Sort {
    public static final int MAX_TO_MIN = 0;
    public static final int MIN_TO_MAX = 1;

    public static void main(String args[]) {
        // 测试用的方法
        int a[] = {1, 54, 6, 3, 78, 34, 12, 45};
        BubbleSort(a, MIN_TO_MAX);
    }

    /**
     * 冒泡排序
     *
     * @param a    数组
     * @param type 从大到小{@link #MAX_TO_MIN}
     *             从小到大{@link #MIN_TO_MAX}
     */
    private static void BubbleSort(int a[], int type) {
        int temp;
        if (type == MAX_TO_MIN) {
            for (int i = 0; i < a.length - 1; i++) { // 取第i个数
                for (int j = 0; j < a.length - 1 - i; j++) { //
                    if (a[j] < a[j + 1]) { // 两数做对比
                        temp = a[j]; // 交换两元素
                        a[j] = a[j + 1];
                        a[j + 1] = temp;
                    }
                }
            }
        } else {
            for (int i = 0; i < a.length - 1; i++) {
                for (int j = i + 1; j < a.length - 1 - i; j++) {
                    if (a[j] > a[j + 1]) {
                        temp = a[j];
                        a[j] = a[j + 1];
                        a[j + 1] = temp;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }
}
