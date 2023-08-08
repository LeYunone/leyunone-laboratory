package com.leyunone.laboratory.core.annotate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public class GoTest {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Test test = new Test();
        Class<?> clazz = Test.class;
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            Check annotation = method.getAnnotation(Check.class);
            if(annotation.required()){
                System.out.println("删除校验");
            }
            method.invoke(test);
        }
    }
}
