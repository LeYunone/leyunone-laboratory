package com.leyunone.laboratory.core.concurrent.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.*;

/**
 * :)
 * 支持子线程共享主线程资源的local
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/7/13
 */
public class ThreadShareLocal {

    private static final ThreadLocal<Integer> ttlLocal = new TransmittableThreadLocal<>();
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws InterruptedException {
//        itl();
        ttl();
    }

    public static void itl() throws InterruptedException {
        final ThreadLocal<Integer> itl = new InheritableThreadLocal<>();
        final ThreadLocal<Integer> tl = new ThreadLocal<>();
        itl.set(1);
        tl.set(1);
        Runnable runnable = () -> {
            System.out.println("子线程itl1:" + itl.get());
            System.out.println("子线程tl1:" + tl.get());
        };
        Runnable runnable2 = () -> {
            System.out.println("子线程itl2:" + itl.get());
            System.out.println("子线程tl1:" + tl.get());
        };
        threadPoolExecutor.submit(runnable);
        threadPoolExecutor.submit(runnable2);
    }

    public static void ttl() throws InterruptedException {
        ttlLocal.set(1);
        Runnable runnable = () -> {
            System.out.println("子线程1:" + ttlLocal.get());
            ttlLocal.set(2);
        };

        threadPoolExecutor.submit(TtlRunnable.get(runnable));
        threadPoolExecutor.submit(TtlRunnable.get(runnable));
    }

    public static void pool(){
        TtlExecutors.getTtlExecutor(threadPoolExecutor);
    }

}
