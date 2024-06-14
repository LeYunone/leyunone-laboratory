package com.leyunone.laboratory.core.redis;

import org.springframework.data.redis.core.script.RedisScript;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/06/15
 */
public interface CacheManager {

    default Long getDefaultTime(){
        return 15*60000l;
    }

    /**
     * 添加string类型
     * @param key keyServletUtils
     * @param value value
     * @return  boolean
     */
    boolean addDate(String key, String value);

    <T> boolean add(String key,T value,Long time);

    <T> boolean add(String key, T value,TimeUnit unit, Long time);

    <T> boolean add(String key,T value);

    <T> boolean add(Map<String,T> values,Long time);

    /**
     * 添加string类型
     * @param key key
     * @param value value
     * @param expireTime 设置超时时间 单位s
     * @return boolean
     */
    boolean addDate(String key, String value, Long expireTime);

    /**
     * 添加string类型
     * @param key key
     * @param value value
     * @param expireTime 设置超时时间 单位ms
     * @return boolean
     */
    boolean addDate(String key, String value, long expireTime, TimeUnit timeUnit);

    /**
     * 新增hash key,在原本的实效时间基础上和设置的的失效时间进行比对来决定最终的失效时间,慎用
     * @param key
     * @param hashKey
     * @param value
     * @param expireTime
     */
    void addDate(String key,String hashKey,Object value,long expireTime);

    /**
     * 批量添加数据
     * @param maps
     * @param expire
     */
    void addBatchWithDate(Map<String,String> maps, long expire);

    /**
     * 改变键值对的value值而不更新过期时间
     * @param key key
     * @param value value
     * @return
     */
    boolean updateValueNotExpireTime(String key, String value);

    /**
     * 添加list类型
     * @param key key
     * @param value value
     * @param isLeft 0:先进后出 1:先进先出
     * @return boolean
     */
    boolean addDate(String key, List value, int isLeft);

    /**
     * hash类型
     * @param key key
     * @param value value
     * @return boolean
     */
    boolean addDate(String key, HashMap value);

    /**
     * 无序set
     * @param key key
     * @param value value
     * @return return
     */
    boolean addDate(String key, String... value);

    /**
     * 有序set
     * @param key key
     * @param value value
     * @return boolean
     */
    boolean addDate(String key, TreeSet value);

    /**
     * String类型查询
     * @param key  key
     * @param <T> 类型
     * @param clazz 目标class
     * @return return
     */
    <T> T getData(String key,Class<T> clazz);

    <T> T get(String key,Class<T> clazz);

    <T> Optional<T> get(String key,Long time, Supplier<T> supplier);

    /**
     * 批量查询数据并为miss key重新查询并缓存
     * @param <T>
     * @param keys
     * @param time
     * @param getMissData
     * @param generateKey
     * @return
     */
    <T> Optional<List<T>> get(List<Object> keys,
                              Long time,
                              Function<List<T>,List<T>> getMissData,
                              Function<T,String>  generateKey);

    <T> Optional<T> get(String key);

    <T> List<T> get(List<Object> keys,Class<T> clazz);

    <T> Optional<List<T>> get(List<Object> keys);

    /**
     * String类型批量查询
     * @param keys keys
     * @param clazz 目标class
     * @param <T> 类型
     * @return <T>List<T>
     */
    <T>List<T> getDatas(List<String> keys,Class<T> clazz);

    /**
     * hash类型查询
     * @param key key
     * @param hashKey hashKey
     * @param <T> <T>
     * @return return
     */
    <T> T getData(String key, String hashKey);

    /**
     * 添加hash类型数据
     * @param key key
     * @param hashKey hashKey
     * @param object 数据
     * @return  boolean
     */
    boolean addDate(String key, String hashKey, Object object);

    /**
     * 删除key
     * @param key key
     * @return boolean
     */
    boolean deleteData(String key);

    void deleteData(List<Object> keys);

    /**
     * 删除hashKey
     * @param key
     * @param hashKey
     * @return
     */
    void deleteData(String key,String hashKey);

    /**
     * 获取hashmap
     * @param key
     * @param keyPrefix
     * @return
     */
    Map<Object,Object> getHashKeyAndValue(String key, String keyPrefix);


    /**
     * 获取hashmap
     * @param key
     * @return hashKey
     */
    Map<Object,Object> getHashKeyAndValue(String key);

    /**
     * 根据Key前缀 获取对象
     * @param key
     * @param <T>
     * @return
     */
    <T> List<T> getValueStartWithKey(String key,Class<T> clazz);

    /**
     * keys
     * @param keys
     */
    void deleteBatchData(Collection<Object> keys);

    /**
     * 设置过期时间
     * @param key
     * @param expire 过期时间 单位秒
     */
    void expire(String key, Long expire);

    boolean execute(RedisScript<Boolean> script, List<Object> keys, Object... args);

    boolean exists(String key);

    <T> Map<String, T> getKeyValueDatas(List<String> keys, Class<T> clazz);
}
