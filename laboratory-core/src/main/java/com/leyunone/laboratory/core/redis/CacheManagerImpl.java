package com.leyunone.laboratory.core.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/06/15
 */
@Slf4j
@Component
public class CacheManagerImpl implements CacheManager {


    final
    private RedisTemplate<Object,Object> redisTemplate;

    public CacheManagerImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean addDate(String key, String value) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForValue().set(key,value);
        return true;
    }

    @Override
    public <T> boolean add(String key, T value, Long time) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForValue().set(key,value,time, TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public <T> boolean add(String key, T value,TimeUnit unit, Long time) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForValue().set(key,value,time, unit);
        return true;
    }

    @Override
    public <T> boolean add(String key, T value) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForValue().set(key,value);
        return true;
    }

    @Override
    public <T> boolean add(Map<String, T> values, Long time) {
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        //批量set数据
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (String key : values.keySet()) {
                T value = values.get(key);
                connection.setEx(key.getBytes(), time, Objects.requireNonNull(valueSerializer.serialize(value),"cache value serializer error"));
            }
            return null;
        });
        return true;
    }

    @Override
    public boolean addDate(String key, String value, Long expireTime) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForValue().set(key,value,expireTime, TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public boolean addDate(String key, String value, long expireTime,TimeUnit timeUnit) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForValue().set(key,value,expireTime,timeUnit);
        return true;
    }

    @Override
    public void addDate(String key, String hashKey, Object value, long expireTime) {
        Objects.requireNonNull(key,"key can't be null");
        Objects.requireNonNull(hashKey,"hashKey can't be null");
        Objects.requireNonNull(value,"value can't be null");
        redisTemplate.opsForHash().put(key,hashKey,value);
        Long expire = redisTemplate.getExpire(key);
        expireTime = null == expire ? expireTime : Long.max(expire,expireTime);
        redisTemplate.expire(key,expireTime,TimeUnit.MILLISECONDS);
    }

    @Override
    public void addBatchWithDate(Map<String,String> maps, long expire) {
        if (CollectionUtils.isEmpty(maps)) {
            return;
        }
        //批量set数据
        redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            for (String key : maps.keySet()) {
                String value = maps.get(key);
                connection.setEx(key.getBytes(), expire, JSONObject.toJSONString(value).getBytes());
            }
            return null;
        });
    }

    @Override
    public boolean updateValueNotExpireTime(String key, String value) {
        if (key.isEmpty()){
            return false;
        }
        redisTemplate.opsForValue().set(key,value,0);
        return true;
    }

    @Override
    public boolean addDate(String key, List value, int isleft) {
        if(key.isEmpty()){
            return false;
        }
        switch (isleft){
            case 0:
                redisTemplate.opsForList().leftPushAll(key,value);
                break;
            case 1:
                redisTemplate.opsForList().rightPushAll(key,value);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean addDate(String key, HashMap value) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForHash().putAll(key,value);
        return true;
    }



    @Override
    public boolean addDate(String key, String ... value) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForSet().add(key,value);
        return true;
    }

    @Override
    public boolean addDate(String key, TreeSet value) {
        if(key.isEmpty()){
            return false;
        }
        redisTemplate.opsForZSet().add(key,value);
        return true;
    }

    @Override
    public <T> T getData(String key,Class<T> clazz) {
        Object o = redisTemplate.opsForValue().get(key);
        boolean primitive = clazz.isPrimitive();
        if(primitive || clazz.equals(String.class)){
            return TypeUtils.cast(o, clazz, ParserConfig.getGlobalInstance());
        }
        if(null == o){
            return null;
        }
        return (T) JSON.toJavaObject(JSON.parseObject(o.toString()), clazz);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> Optional<T> get(String key, Long time, Supplier<T> supplier) {
        Optional<T> optional = get(key);
        if (optional == null || !optional.isPresent()) {
            T t = supplier.get();
            if( t != null ) {
                log.debug("Get entity from onEmpty callback for value : " + t.toString());
            }else{
                log.debug("Get entity from onEmpty callback for value null");
            }
            if( time == null ){
                time = getDefaultTime() ;
            }
            if( t == null ){
                return Optional.empty();
            }
            add(key, t, time);
            return Optional.of( t );
        }
        return optional;
    }

    @Override
    public <T> Optional<List<T>> get(List<Object> keys, Long time, Function<List<T>, List<T>> getMissData, Function<T, String> generateKey) {
        Optional<List<T>> optional = get(keys);
        List<T> hitData = new ArrayList<>();
        if(null != optional && optional.isPresent()){
            hitData = optional.get();
        }
        List<T> missData = getMissData.apply(hitData);
        if(!CollectionUtils.isEmpty(missData)){
            if( time == null ){
                time = getDefaultTime() ;
            }
            Long finalTime = time;
            missData.forEach(t -> {
                add(generateKey.apply(t),t,finalTime);
            });
            hitData.addAll(missData);
        }
        if(CollectionUtils.isEmpty(hitData)){
            return Optional.empty();
        }else {
            return Optional.of(hitData);
        }
    }

    @Override
    public <T> Optional<List<T>> get(List<Object> keys) {
        if(CollectionUtils.isEmpty(keys)){
            return  Optional.empty();
        }
        List<Object> multiGet = redisTemplate.opsForValue().multiGet(keys);
        List<T> collect = multiGet.stream().filter(Objects::nonNull).map(v -> (T) v).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect)){
            return Optional.empty();
        }else {
            return Optional.of(collect);
        }
    }

    @Override
    public <T> Optional<T> get(String key) {
        if( key == null ){
            return  Optional.empty();
        }else{
            T t = (T)redisTemplate.opsForValue().get(key);
            if( t == null ){
                return Optional.empty();
            }else{
                return Optional.of(t);
            }
        }
    }

    @Override
    public <T> List<T> get(List<Object> keys, Class<T> clazz) {
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        if(CollectionUtils.isEmpty(objects)){
            return new ArrayList<>();
        }
        return objects.stream().filter(Objects::nonNull).map(v -> (T) v).collect(Collectors.toList());
    }

    @Override
    public <T> List<T> getDatas(List<String> keys, Class<T> clazz) {
        //通过管道批量获取数据
        List<Object> values = redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            for (String key : keys) {
                connection.get(key.getBytes());
            }
            return null;
        });
        //标示，若数据为基本数据类型或Sting类型
        boolean primitive = clazz.isPrimitive();
        boolean isPrimitiveOrString= primitive || clazz.equals(String.class);
        List<T> newList=new ArrayList<T>();
        for(Object value:values){
            //若查找的结果为null，过滤
            if(value==null){
                continue;
            }
            if(isPrimitiveOrString){
                newList.add(TypeUtils.cast(value, clazz, ParserConfig.getGlobalInstance()));
            }else {
                newList.add((T) JSON.toJavaObject(JSON.parseObject(value.toString()), clazz));
            }
        }
        return newList;
    }

    @Override
    public <T> T getData(String key, String hashKey) {
        T value = (T)redisTemplate.opsForHash().get(key,hashKey);
        return value;
    }


    @Override
    public boolean addDate(String key, String hashKey, Object object) {
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(hashKey)){
            return false;
        }
        redisTemplate.opsForHash().put(key,hashKey,object);
        return true;
    }

    @Override
    public boolean deleteData(String key) {
        if (StringUtils.isEmpty(key)){
            return false;
        }
        redisTemplate.delete(key);
        return true;
    }

    @Override
    public void deleteData(List<Object> keys) {
        if(CollectionUtils.isEmpty(keys)){
            return;
        }
        redisTemplate.delete(keys);
    }

    @Override
    public void deleteData(String key, String hashKey) {
        Objects.requireNonNull(key,"key can't be null");
        Objects.requireNonNull(hashKey,"hashKey can't be null");
        redisTemplate.opsForHash().delete(key,hashKey);
    }

    @Override
    public Map<Object, Object> getHashKeyAndValue(String key, String keyPrefix) {
        if (StringUtils.isEmpty(key)){
            return null;
        }
        StringBuffer jointKey = new StringBuffer(keyPrefix);
        jointKey.append(key);
        key = jointKey.toString();
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    @Override
    public Map<Object, Object> getHashKeyAndValue(String key) {
        if (StringUtils.isEmpty(key)){
            return null;
        }
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public <T> List<T> getValueStartWithKey(String key,Class<T> clazz) {
        if (StringUtils.isEmpty(key)){
            return null;
        }
        Set rawKeys = redisTemplate.keys(key+"*");
        List<T> lists = new ArrayList<>();
        int i = 0;
        for(Object o : rawKeys){
            lists.add(getData((String)o,clazz));
        }
        return lists;
    }

    @Override
    public void deleteBatchData(Collection<Object> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public void expire(String key, Long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean execute(RedisScript<Boolean> script, List<Object> keys, Object... args) {
        return Boolean.TRUE.equals(redisTemplate.execute(script, keys, args));
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public <T> Map<String, T> getKeyValueDatas(List<String> keys, Class<T> clazz) {
        //通过管道批量获取数据
        List<Object> values = redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            for (String key : keys) {
                connection.get(key.getBytes());
            }
            return null;
        });
        //标示，若数据为基本数据类型或Sting类型
        boolean primitive = clazz.isPrimitive();
        boolean isPrimitiveOrString = primitive || clazz.equals(String.class);
        Map<String, T> data = new HashMap<>();
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            //若查找的结果为null，过滤
            if (value == null) {
                continue;
            }
            if (isPrimitiveOrString) {
                data.put(keys.get(i), TypeUtils.cast(value, clazz, ParserConfig.getGlobalInstance()));
            } else {
                data.put(keys.get(i), (T) JSON.toJavaObject(JSON.parseObject(value.toString()), clazz));
            }
        }
        return data;
    }
}
