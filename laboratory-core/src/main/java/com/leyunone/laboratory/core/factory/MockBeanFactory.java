package com.leyunone.laboratory.core.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 单元测试 mock工厂 
 * @author leyunone
 * @create 2022/8/30
 */
public class MockBeanFactory {

    private volatile static MockBeanFactory factory;

    private MockBeanFactory() {
    }

    public static MockBeanFactory buildMockBeanFactory() {
        if (ObjectUtil.isNull(factory)) {
            synchronized (MockBeanFactory.class) {
                if (ObjectUtil.isNull(factory)) {
                    factory = new MockBeanFactory();
                }
            }
        }
        return factory;
    }

    private Map<Class<?>, Object> map = new HashMap<>();

    /**
     * 获得内置全被mock的对象
     * 只拿最长的构造方法构建
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @SneakyThrows
    public <T> T getBeMockBean(Class<T> clazz) {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Constructor allParamConstr = null;
        Class[] paramters = null;
        int len = Integer.MIN_VALUE;
        for (Constructor constructor : declaredConstructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length > len) {
                allParamConstr = constructor;
                paramters = parameterTypes;
                len = parameterTypes.length;
            }
        }
        Object[] params = new Object[paramters.length];
        for (int i = 0; i < paramters.length; i++) {
            Object mock = Mockito.mock(paramters[i]);
            params[i] = mock;
            map.put(paramters[i], mock);
        }
        return (T) allParamConstr.newInstance(params);
    }

    /**
     * 获得内置部分mock对象
     *
     * @param clazz
     * @param exclude 排除非mock对象
     * @param <T>
     * @return
     */
    @SneakyThrows
    public <T> T getBeMockBean(Class<T> clazz, Object... exclude) {
        if (exclude.length == 0) return this.getBeMockBean(clazz);
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Constructor allParamConstr = null;
        Class[] paramters = null;
        int len = Integer.MIN_VALUE;
        Map<? extends Class<?>, Object> excludeMap = CollectionUtil.newArrayList(exclude).stream().collect(Collectors.toMap(Object::getClass, Function.identity()));
        for (Constructor constructor : declaredConstructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length > len) {
                allParamConstr = constructor;
                paramters = parameterTypes;
                len = parameterTypes.length;
            }
        }
        Object[] params = new Object[paramters.length];
        for (int i = 0; i < paramters.length; i++) {
            Class<?> param = paramters[i];
            Object excludeObj = getExclude(excludeMap, param);
            if(ObjectUtil.isNotNull(excludeObj)){
                params[i] = excludeObj;
            }else{
                Object mock = Mockito.mock(paramters[i]);
                params[i] = mock;
                map.put(paramters[i], mock);
            }
        }
        return (T) allParamConstr.newInstance(params);
    }

    /**
     * 无参构造构建全mock对象
     * @param clazz
     * @param <T>
     * @return
     */
    @SneakyThrows
    public <T> T getBeMockBeanNoConstructor(Class<T> clazz){
        T t = clazz.newInstance();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for(Field field : declaredFields){
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Class<?> type = field.getType();
            Object mock = Mockito.mock(type);
            field.set(t,mock);
            field.setAccessible(accessible);
            map.put(type,mock);
        }
        return t;
    }

    /**
     * 无参构造构建部分mock对象
     * @param clazz
     * @param exclude auto注入对象
     * @param <T>
     * @return
     */
    @SneakyThrows
    public <T> T getBeMockBeanNoConstructor(Class<T> clazz, Object... exclude){
        if(ObjectUtil.isNull(exclude)) return this.getBeMockBeanNoConstructor(clazz);
        T t = clazz.newInstance();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        Map<? extends Class<?>, Object> excludeMap = CollectionUtil.newArrayList(exclude).stream().collect(Collectors.toMap(Object::getClass, Function.identity()));
        for(Field field : declaredFields){
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Class<?> type = field.getType();
            Object excludeObj = getExclude(excludeMap, type);
            if(ObjectUtil.isNotNull(excludeObj)){
                field.set(t,excludeObj);
            }else {
                Object mock = Mockito.mock(field.getType());
                field.set(t,mock);
                map.put(type,mock);
            }
            field.setAccessible(accessible);
        }
        return t;
    }

    private Object getExclude(Map<? extends Class<?>, Object> map, Class<?> clazz) {
        Set<? extends Class<?>> classes = map.keySet();
        for (Class<?> mapClass : classes) {
            if(clazz.isAssignableFrom(mapClass)){
                return map.get(mapClass);
            }
        }
        return null;
    }

    public <T> T getMockBean(Class<T> clazz) {
        return (T) map.get(clazz);
    }
}
