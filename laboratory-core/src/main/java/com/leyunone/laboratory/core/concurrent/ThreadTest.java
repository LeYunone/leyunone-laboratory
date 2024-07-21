package com.leyunone.laboratory.core.concurrent;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/7/15
 */
public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 3, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for(int i=0;i<=4;i++){
            threadPoolExecutor.execute(() -> {
                System.out.println("执行了");
                countDownLatch.countDown();
            });
        }
        boolean await = countDownLatch.await(300, TimeUnit.MILLISECONDS);

        System.out.println("主线程:"+await);
    }

    ;
}
