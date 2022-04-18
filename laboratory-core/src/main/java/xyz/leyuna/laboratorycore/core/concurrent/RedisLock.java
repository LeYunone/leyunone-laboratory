package xyz.leyuna.laboratorycore.core.concurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pengli
 * @date 2022-04-18
 * 使用Redis 自定义一把分布式锁
 */
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    //阻塞线程
    private static BlockingQueue<Thread> queue = new LinkedBlockingQueue();
    
    public void lock(){
        //查询是否可以从redis中拿到锁，如果拿到则设置当前锁的过期时间30秒
        while (!redisTemplate.opsForValue().setIfAbsent("redisLock", "yes", 30, TimeUnit.SECONDS)){
            queue.add(Thread.currentThread());
            //如果拿不到，则阻塞这个线程，直到获得锁
            LockSupport.park();
            //当被唤醒时，继续争夺这把锁
            queue.remove(Thread.currentThread());
        }
    }
    
    public void unLock(){
        for(Thread thread:queue){
            LockSupport.unpark(thread);
        }
    }
}
