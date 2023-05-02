package com.leyunone.laboratory.core.algorithm;

/**
 * 沙雕代码 睡眠排序
 */

import java.util.concurrent.CopyOnWriteArrayList;

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
        CopyOnWriteArrayList<Integer> cwa = new CopyOnWriteArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            long time = arr[i];
            final int j = i;
            new Thread(() ->{
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cwa.add(arr[j]);
            }).start();
        }
        while(cwa.size()!=arr.length){
        }
        for(Integer num:cwa){
            System.out.println(num);
        }
    }
}
