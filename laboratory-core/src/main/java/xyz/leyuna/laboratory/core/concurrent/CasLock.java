package xyz.leyuna.laboratory.core.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author pengli
 * @date 2022-04-15
 * 自定义一个 并且优化的cas锁
 */
public class CasLock {
    
    //存放caskey的线程表头
    AtomicReference<Long> key = new AtomicReference<>();
    //阻塞队列， 有区域时加入，否则阻塞线程执行
    BlockingQueue<Thread> dThread = new LinkedBlockingQueue<>();
    
    public void lock(){
        //当前线程
        Thread thread = Thread.currentThread();
        //设置自旋 如果发现key值被改变，则说明有锁在竞争，进入锁流程
        while(!key.compareAndSet(null,thread.getId())){
            dThread.offer(thread);
            System.out.println("阻塞 进入一个线程");
            LockSupport.park();;
            dThread.remove(thread);
        }
    }
    
    public void unLock(){
        //如果还是当前锁的话，就解放锁
        if(key.compareAndSet(Thread.currentThread().getId(),null)){
            for(Thread thread:dThread){
                //放开所有线程，去争夺资源
                LockSupport.unpark(thread);
            }
        }
    }
}
