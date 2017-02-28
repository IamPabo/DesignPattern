package com.example.algorithm.sort;

/**
 * 简单选择排序
 * 基本思想：在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
 * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
 *
 * @auther MaxLiu
 * @time 2017/2/28
 */

class SelectSort {

    static int[] selectSort(int[] arr) {
        int position;
        for (int i = 0; i < arr.length; i++) {
            int j = i + 1;
            position = i;
            int temp = arr[i];
            for (; j < arr.length; j++) {
                if (arr[j] < temp) {
                    temp = arr[j];
                    position = j;
                }
            }
            arr[position] = arr[i];
            arr[i] = temp;
        }
        for (int anArr : arr) {
            System.out.println(anArr);
        }
        return arr;
    }
}
