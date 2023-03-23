package com.leyunone.laboratory.core.function;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-06
 */
@FunctionalInterface
public interface UniqueFunction<T,R> {

    R getRule(T t);
}
