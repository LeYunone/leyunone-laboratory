package com.leyunone.laboratory.core.transactional;

import java.util.concurrent.CompletableFuture;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/7/14
 */
public class ThreadLocalTransactional {

    private static final ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<Integer> integerInheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        new ThreadLocalTransactional().mainThread();
    }

    public void mainThread() {
        integerInheritableThreadLocal.set(1);

        CompletableFuture.runAsync(()->{
            System.out.println(integerInheritableThreadLocal.get());
        });
        System.out.println(integerInheritableThreadLocal.get());
    }
}
