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

    /**
     * 冒泡排序
     * 冒泡排序算法的运作如下：（从后往前）
     * 1.比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 2.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 3.针对所有的元素重复以上的步骤，除了最后一个。
     * 4.持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     *
     * @param a 数组
     */


    /**
     * 快速排序算法
     * 基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，
     * 将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,此时基准元素
     * 在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。
     *
     * @param arr   排序的数组
     * @param start 开始的索引
     * @param end   结束的索引
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            // s是分裂位置，即排序结果序列的最终位置
            int s = HoarePartition(arr, start, end);
            quickSort(arr, start, s - 1);
            quickSort(arr, s + 1, start);
        }
    }

    /**
     * 以数组第一个元素为中轴，对子数组进行划分
     * 返回分裂点在数组排序结果中的最终位置
     */
    private static int HoarePartition(int[] arr, int start, int end) {
        int p = arr[start];
        int i = start + 1;
        int j = end;
        if (i == j) {
            if (arr[j] >= p) {//此时分裂点右方只有一个元素，且大于分裂点处值
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
                    if (arr[j] >= p) {  //此时为分裂点右方全部是大于等于p的元素
                        System.out.println(Arrays.toString(arr));
                        return start;
                    }
                    break;
                }
                j -= 1;
            }
            swapArray(arr, i, j);
        }
        swapArray(arr, i, j);    //当i>=j撤换最后一次交换
        swapArray(arr, j, start);
        System.out.println(Arrays.toString(arr));
        return j;
    }

    /**
     * 交换数组中两个不同位置上的元素值
     */
    private static void swapArray(int[] arr, int m, int n) {
        int temp = arr[m];
        arr[m] = arr[n];
        arr[n] = temp;
    }

}
