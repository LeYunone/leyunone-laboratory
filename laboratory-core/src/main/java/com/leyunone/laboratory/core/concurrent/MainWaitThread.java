package com.leyunone.laboratory.core.concurrent;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 主线程等待子线程任务完成 案例
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-03
 */
public class MainWaitThread {

    volatile int state =0;
    
    public static void main(String[] args) throws InterruptedException {
//        joinThread();
        countlatchdown();
    }
    
    public static void waitFlag() {
        
    }

    public static void countlatchdown() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(4);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i <= 3; i++) {
            final int count = i;
            executorService.submit(() -> {
                        try {
                            TimeUnit.SECONDS.sleep(count);
                            System.out.println("子线程完成" + count+"当前："+countDownLatch.getCount());
                            countDownLatch.countDown();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        countDownLatch.await();
        System.out.println("主线程完成");
    }

    public static void joinThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("子线程完成1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("子线程完成2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join(1111);
        thread2.start();
//        thread2.join();

        System.out.println("主线程任务");
    }
}
