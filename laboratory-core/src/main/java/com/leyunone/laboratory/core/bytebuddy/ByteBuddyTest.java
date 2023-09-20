package com.leyunone.laboratory.core.bytebuddy;

import com.leyunone.laboratory.core.bean.Person;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-15
 */
public class ByteBuddyTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        buddyTest1();
        buddyBuildObject();
    }

    /**
     * 方法重写
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void buddyTest1() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Hello.class)
                .method(ElementMatchers.named("testHellow"))
                .intercept(FixedValue.value("Hello World"))
                .make()
                .load(Hello.class.getClassLoader())
                .getLoaded();
        Hello instance = (Hello)dynamicType.newInstance();
        String toString = instance.testHellow();

        System.out.println(toString);
        System.out.println(instance.getClass().getCanonicalName());
    }

    public static void buddyBuildObject() throws IllegalAccessException, InstantiationException {
        Class<? extends Hello> loaded = new ByteBuddy()
                .subclass(Hello.class)
                .defineProperty("my",String.class)
                .defineField("name", String.class,1)
                .defineField("age", Integer.class,1)
                .make().load(Hello.class.getClassLoader()).getLoaded();
        Hello hello = loaded.newInstance();
        System.out.println(hello.testHellow());
    }
}
