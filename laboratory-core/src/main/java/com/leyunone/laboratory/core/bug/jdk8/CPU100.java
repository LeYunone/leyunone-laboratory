package com.leyunone.laboratory.core.bug.jdk8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/12/19
 */
public class CPU100 {

    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ScheduledExecutorService e = Executors.newScheduledThreadPool(0);
        //绑定到 5 号 CPU 上执行
        for (; ; ) {
            try {
                Runnable r = workQueue.poll(0, TimeUnit.NANOSECONDS);
                if (r != null) break;
            } catch (InterruptedException retry) {
            }
        }
    }

}
