package com.leyunone.laboratory.core.concurrent;

import cn.hutool.core.lang.UUID;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/4/3
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        System.out.println("我已经收到了:"+t());
    }


    public static String t(){
        Executor executor = new ThreadPoolExecutor(1, 2, 1L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

//        executor.execute(()->{
//            try {
//                System.out.println("线程一开始，时间："+System.currentTimeMillis());
//                Thread.sleep(5000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("业务开始，时间："+System.currentTimeMillis());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {

            }
            System.out.println("开启供应链头,组装消息");
            System.out.println("组装");
            String message = "message";
            return message;
        }, executor);

        completableFuture.whenComplete((message, exception) -> {

            System.out.println("消息发送:" + message);
        });
        return "ok";
    }
}

