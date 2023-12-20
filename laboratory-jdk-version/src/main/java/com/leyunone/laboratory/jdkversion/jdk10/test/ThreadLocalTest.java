package com.leyunone.laboratory.jdkversion.jdk10.test;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/12/19
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        // 创建线程局部变量
        ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

        // 获取线程局部变量的值
        int value = count.get();

        // 设置线程局部变量的值
        count.set(value + 1);
    }
}
