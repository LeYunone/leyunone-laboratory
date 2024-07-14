package com.leyunone.laboratory.core.concurrent.threadlocal;

import org.springframework.transaction.annotation.Transactional;

/**
 * :)
 *  支持子线程共享主线程资源的local
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/7/13
 */
public class ThreadShareLocal {


    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    }

}
