package com.leyunone.laboratory.core.concurrent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * :)
 * 交替打印AB案例
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-02
 */
public class AlternatingOut {

    public static void main(String[] args) {
        twoThread();
    }
    
    public static void twoThread(){
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);

        new Thread(()->{
            int i =0;
            while (i!=50){
                try {
                    semaphoreA.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                semaphoreB.release();
                i++;
            }
        }).start();
        
        new Thread(()->{
            int i =0;
            while (i!=50){
                try {
                    semaphoreB.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("B");
                semaphoreA.release();
                i++;
            }
        }).start();
    }
}
