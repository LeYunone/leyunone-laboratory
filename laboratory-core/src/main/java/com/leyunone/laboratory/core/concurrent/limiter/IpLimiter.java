package com.leyunone.laboratory.core.concurrent.limiter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/7/23
 */
@Aspect
@Component
public class IpLimiter {
    //生成速率
    private volatile double DEFAULT_LIMITER_COUNT = 0.5;
    //十分钟消失的缓存ip限流器
    private Cache<String, RateLimiter> limiterCache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build();

    @Autowired
    private HttpServletRequest request;

    @Around("execution(* com.leyunone.laboratory..*controller.*())")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //代理透传过来的ip
        String realIp = request.getHeader("X-Real-IP");
        Signature signature = proceedingJoinPoint.getSignature();
        String key = String.join("_", realIp, proceedingJoinPoint.getTarget().getClass().getName() + "_" + signature.getName());
        RateLimiter rateLimiter = limiterCache.get(key, () -> RateLimiter.create(DEFAULT_LIMITER_COUNT));
        if (!rateLimiter.tryAcquire()) {
            //限流
            throw new RuntimeException("ip limiter");
        }
        return proceedingJoinPoint.proceed();
    }

    public void updateCount(double count) {
        DEFAULT_LIMITER_COUNT = count;
        limiterCache.asMap().values().forEach(limiter -> limiter.setRate(count));
    }
}
