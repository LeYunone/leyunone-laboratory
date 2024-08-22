package com.leyunone.laboratory.core.redis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 数据收集桶
 */
public class DataCollectHandler {

    private final static Logger logger = LoggerFactory.getLogger(DataCollectHandler.class);

    /**
     * 频率入库阈值
     * 按照设备最短每秒上报一次，最坏情况是隔n秒入库一次
     */
    @Value("${limit.collect.threshold:10}")
    private final int threshold = 10;
    private final CacheManager cacheManager;

    /**
     * 缓存过期 = 数据落库
     */
    private final Cache<String, LogCache> localCache = CacheBuilder.newBuilder()
            .maximumSize(801).removalListener(new RemovalListener<String, LogCache>() {
                @Override
                public void onRemoval(RemovalNotification<String, LogCache> notification) {
                    LogCache logCache = notification.getValue();
                    if (ObjectUtil.isNotNull(logCache.getAttrLogs())) {
                        logCache.getAttrLogs().add(logCache.getLastAttr());
                    }
                    //saveBatch(logCache.getAttrLogs());
                }
            }).expireAfterWrite(1, TimeUnit.MINUTES).build();

    public DataCollectHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     *
     */
    public void handler(String key, String newData) {
        /**
         * 一分钟内属性值变化10次时，第10次强制入库
         * 否则取最后一次/第一次当作本分钟的属性值
         */
        boolean local = localCache.size() <= 800;
        List<String> attrLogs = new ArrayList<>();

        /**
         *  本地缓存，聚合一分钟内的数据入一次库
         *  redis缓存，入库判定成功即入库
         */

        /**
         * 该缓存逻辑为： 防止A设备进入到redis收集后，本地缓存放开，又进入到本地缓存中 反之相同
         */
        boolean inRedis = cacheManager.exists(key);
        if ((local || this.pruneLocalCache(key)) && !inRedis) {
            logger.debug("local cache attrlog:{}", key);
            useLocalCache(key, newData);
        } else {
            logger.debug("redis cache attrlog:{}", key);
            String cacheResult = useRedisCache(key, newData);
            if (ObjectUtil.isNotNull(cacheResult)) {
                attrLogs.add(cacheResult);
            }
        }
        if (CollectionUtil.isNotEmpty(attrLogs)) {
//            saveBatch(attrLogs);
        }

    }


    private boolean pruneLocalCache(String key) {
        LogCache ifPresent = localCache.getIfPresent(key);
        boolean hasLocal = true;
        if (ObjectUtil.isNull(ifPresent)) {
            //通知清理缓存
            localCache.cleanUp();
            hasLocal = false;
        }
        return hasLocal;

    }

    /**
     * 使用本地缓存 聚合一分钟数据批量插入
     */
    private void useLocalCache(String key, String newData) {
        try {
            boolean inStorage = false;

            LogCache logCache = localCache.get(key, () -> {
                LogCache log = new LogCache();
                log.setCurrentWater(newData);
                return log;
            });

            logCache.setFrequency(logCache.getFrequency() + 1);
            //频繁更新推到待入库队列
            if (logCache.getFrequency() >= threshold && !newData.equals(logCache.getCurrentWater())) {
                logCache.setFrequency(0);
                inStorage = true;
            }
            logCache.setLastAttr(newData);
            logCache.setCurrentWater(newData);
            if (inStorage) {
                //最后的埋点置空
                logCache.setLastAttr(null);
                logCache.getAttrLogs().add(newData);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 使用redis缓存兜底 实时记录
     */
    private String useRedisCache(String dataKey, String newValue) {

        LogCache logCache = new LogCache();
        boolean inStorage = true;
        String key = dataKey;
        String inStorageResult = null;
        boolean hasCool = cacheManager.exists(key);
        if (hasCool) {
            inStorage = false;
            synchronized (key) {
                //频繁更新的属性直接入库一次
                logCache = cacheManager.getData(key, LogCache.class);
                logCache.setFrequency(logCache.getFrequency() + 1);
                if (!logCache.getCurrentWater().equals(newValue) && logCache.getFrequency() >= threshold) {
                    logCache.setFrequency(0);
                    inStorage = true;
                }
                logCache.setCurrentWater(newValue);
                cacheManager.upDataValueNotExpireTime(key, JSONObject.toJSONString(logCache));
            }

        } else {
            logCache.setCurrentWater(newValue);
            cacheManager.addData(key, JSONObject.toJSONString(logCache), 1, TimeUnit.MINUTES);
        }

        if (inStorage) {
            inStorageResult = newValue;
        }
        return inStorageResult;
    }

    @Data
    public static class LogCache {

        /**
         * 当前水位值
         */
        private String currentWater;
        /**
         * 频率值
         */
        private Integer frequency = 0;
        /**
         * 最后的值上报
         */
        private String lastAttr;

        private List<String> attrLogs = new ArrayList<>();
    }
}
