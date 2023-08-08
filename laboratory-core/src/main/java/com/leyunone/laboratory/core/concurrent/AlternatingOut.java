package com.leyunone.laboratory.core.concurrent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * :)
 * 交替打印AB案例
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-02
 */
public class AlternatingOut {

    public static void main(String[] args) {
    }
    
    public static void twoThread(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println("A");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println("B");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
