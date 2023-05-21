package com.leyunone.laboratory.core.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-15
 */
public class ByteBuddyTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World"))
                .make()
                .load(Hello.class.getClassLoader())
                .getLoaded();
        Object instance = dynamicType.newInstance();
        String toString = instance.toString();
        
        System.out.println(toString);
        System.out.println(instance.getClass().getCanonicalName());
    }
}
