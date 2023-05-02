package com.leyunone.laboratory.core.algorithm;

/**
 * 沙雕代码 睡眠排序
 */

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-01
 */
public class SleepSort {

    public static void main(String[] args) {
        sleepSort();
    }

    public static void sleepSort() {
        int[] arr = new int[]{1, 5, 3, 2, 8,4,100,400,200};
        for (int i = 0; i < arr.length; i++) {
            long time = arr[i];
            new Thread(() ->{
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(time);
            }).start();
        }
    }
}
