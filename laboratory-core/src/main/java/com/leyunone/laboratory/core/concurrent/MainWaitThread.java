package com.leyunone.laboratory.core.concurrent;


import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 主线程等待子线程任务完成 案例
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-03
 */
public class MainWaitThread {

    volatile int state =0;
    
    public static void main(String[] args) throws Exception {
//        joinThread();
//        countlatchdown();
//          waitFlag();
        completableFuture();
//        threadPoolWait();
    }
    
    public static void threadPoolWait() throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i <= 3; i++) {
            final int count = i;
            executorService.submit(() -> {
                        try {
                            TimeUnit.SECONDS.sleep(count);
                            System.out.println("子线程任务"+count);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        boolean b = executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("主线程完成"+b);
    }
    
    public static void completableFuture() throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<String> thread1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "子线程任务一";
        });
        CompletableFuture<String> thread2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "子线程任务二";
        });
        System.out.println(thread1.get());
        System.out.println(thread2.get());
        System.out.println("主线程任务");
    }
    
    public static void waitFlag() {
        AtomicInteger integer = new AtomicInteger(0);
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("子线程任务");
                integer.addAndGet(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        LockSupport.park();
        while(integer.get()!=1 ){
        }
        System.out.println("主线程任务");

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
