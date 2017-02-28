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
        // 简单选择排序
        SelectSort.selectSort(a);
        //BubbleSort.bubbleSort(a);// 冒泡排序
        //quickSort(a, 0, a.length - 1);// 快速排序
    }
}
