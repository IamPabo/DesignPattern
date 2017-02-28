package com.example.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 冒泡排序算法的运作如下：（从后往前）
 * 1.比较相邻的元素。如果第一个比第二个大，就交换他们两个。
 * 2.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
 * 3.针对所有的元素重复以上的步骤，除了最后一个。
 * 4.持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
 *
 * @auther MaxLiu
 * @time 2017/2/28
 */

class BubbleSort {

    static void bubbleSort(int[] a) {
        System.out.println("=======冒泡排序========\n");
        int temp = 0;
        for (int i = 0; i < a.length - 1; i++) { // 取第i个数
            for (int j = 0; j < a.length - 1 - i; j++) { //
                if (a[j] > a[j + 1]) { // 两数做对比
                    temp = a[j]; // 交换两元素
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    System.out.println(Arrays.toString(a));
                }
            }
        }
    }
}
