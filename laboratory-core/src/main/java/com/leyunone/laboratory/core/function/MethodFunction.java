package com.leyunone.laboratory.core.function;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-05
 */
@FunctionalInterface
public interface MethodFunction<T,R> {

    void method(T t,R r);
}
