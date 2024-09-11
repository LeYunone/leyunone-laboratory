package com.leyunone.laboratory.core.concurrent.pool;

import cn.hippo4j.common.executor.support.ResizableCapacityLinkedBlockingQueue;
import cn.hutool.core.util.RandomUtil;
import com.leyunone.laboratory.core.bean.Person;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * :)
 * 动态线程池
 *
 * @Author leyunone
 * @Date 2024/7/22 11:18
 */
public class DynamicThreadPool {

    public static void main(String[] args) throws IOException {
        DynamicThreadPool dynamicThreadPool = new DynamicThreadPool();
        dynamicThreadPool.buildPool();
//        dynamicThreadPool.gcThreadPool();
    }
    
    public void gcThreadPool() {
        LinkedBlockingQueue<Runnable> objects = new LinkedBlockingQueue<>(5);
        ThreadPoolExecutor oldThreadPool =  new ThreadPoolExecutor(1, 1, 100,
                TimeUnit.MILLISECONDS, objects, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // 提交一个任务给旧线程池
        for(int i =1;i<=10;i++) {
            try {
                oldThreadPool.execute(()->{
                    try {
                        System.out.print("执行");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }catch (Exception e){
            }
        }
        objects = null;
        oldThreadPool =  new ThreadPoolExecutor(1, 1, 100,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        List<Person> list = new ArrayList<>();

        try {
            while (true) {
                Person person = new Person();
                person.setId("1");
                person.setName(RandomUtil.randomNumbers(10));
                person.setAge(RandomUtil.randomInt());
                list.add(person);
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Out of Memory Error occurred!");
        }
    }

    public void buildPool() throws IOException {
        LinkedBlockingQueue<Runnable> objects = new LinkedBlockingQueue<>(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 100,
                TimeUnit.MILLISECONDS, objects, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        Scanner sc = new Scanner(System.in);

        while (true) {
            int read = sc.nextInt();
            if (read == 1) {
                System.out.println(threadPoolExecutor.getMaximumPoolSize());
                int threadCount = sc.nextInt();
                for (int i = 1; i <= threadCount; i++) {
                    try {
                        threadPoolExecutor.submit(() -> {
                            System.out.println(threadCount);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        System.out.println("失败了");
                    }
                }
            } else if (read == 2) {
                int max = sc.nextInt();
                threadPoolExecutor.setMaximumPoolSize(max);
            } else if (read == 3) {
                int queue = sc.nextInt();
                objects = new LinkedBlockingQueue<>(queue);
                threadPoolExecutor = new ThreadPoolExecutor(1, 1, 100,
                        TimeUnit.MINUTES, objects, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }else if (read == 4){
                ResizableCapacityLinkedBlockingQueue<Runnable> queue =  new ResizableCapacityLinkedBlockingQueue<>(10);
                threadPoolExecutor = new ThreadPoolExecutor(1, 1, 100,
                        TimeUnit.MINUTES, queue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }else if (read == 5){
                int num = sc.nextInt();
                ResizableCapacityLinkedBlockingQueue<Runnable> queue = (ResizableCapacityLinkedBlockingQueue)threadPoolExecutor.getQueue();
                queue.setCapacity(num);
            }
        }
    }

}
