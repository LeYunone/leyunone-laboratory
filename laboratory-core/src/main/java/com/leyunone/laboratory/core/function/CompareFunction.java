package com.leyunone.laboratory.core.function;

import com.leyunone.laboratory.core.bean.Person;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-06
 * 比较函数
 */
public interface CompareFunction<T> {

    boolean compare(T t1,T t2);

    static boolean compare2(Person t1,Person t2){
        return t1.getAge()>t2.getAge();
    }

}
