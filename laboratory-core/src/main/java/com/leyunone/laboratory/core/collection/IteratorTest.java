package com.leyunone.laboratory.core.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * :)
 * 迭代手法总结
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/11/12
 */
public class IteratorTest {

    public static List<Integer> data = new ArrayList<>();

    static{
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
    }

    public static void main(String[] args) {
        parallelStreamIterator();
    }

    /**
     * 错误的遍历
     */
    public static void errorIteration(){
        for (Integer datum : data) {
            data.remove(2);
        }
    }

    public static void iteratorMethod(){
        Iterator<Integer> iterator = data.iterator();
        while(iterator.hasNext()){
            Integer next = iterator.next();
            if(next.equals(1)) iterator.remove();
        }
    }

    public static void copyToWrite(){
        CopyOnWriteArrayList<Integer> cwa = new CopyOnWriteArrayList<>(data);
        for(int i =0 ;i<cwa.size();i++){
            Integer b = cwa.get(i);
            if(b == 2){
                cwa.remove(i);
            }
        }
    }

    public static void trendsIndex(){
        Integer size = data.size();
        Integer index = 0;
        while(index != size){
            Integer num = data.get(index++);
            if(num == 4){
                size+=1;
                data.add(5);
            }
            System.out.println(num);
        }
    }

    public static void queueIterator(){
        Queue<Integer> queue = new LinkedList<>(data);
        while(!queue.isEmpty()){
            Integer num = queue.poll();
            if(num == 4){
                queue.add(5);
            }
            System.out.println(num);
        }
    }

    public static void parallelStreamIterator(){
        List<Integer> list = new ArrayList<>(data);
        Lock lock = new ReentrantLock();
        list.parallelStream().forEach(adata->{
            lock.lock();
            try {
                if(adata==4){
                    list.add(5);
                }
                System.out.println(adata);
            }finally {
                lock.unlock();
            }
        });
    }
}
