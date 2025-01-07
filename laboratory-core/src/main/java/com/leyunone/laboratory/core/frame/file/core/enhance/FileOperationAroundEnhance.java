package com.leyunone.laboratory.core.frame.file.core.enhance;

import cn.hutool.core.util.ObjectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * :)
 * 增强文件操作前后的逻辑
 *
 * @Author LeYunone
 * @Date 2024/12/23 18:20
 */
@Aspect
@Component
public class FileOperationAroundEnhance {

    @Pointcut("execution(public * com.leyunone.laboratory.core.frame.file..core.UploadFileController.*.*(..))")
    public void pointCut() {
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 执行文件操作需增强的情况
     *
     * @param point
     * @return
     */
    @Around("pointCut()")
    public void enhanceMetohd(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String[] parameterNames = signature.getParameterNames();
        // 获取实际传入的参数值数组
        Object[] args = point.getArgs();

        // 查找是否存在转向方法id
        boolean hasForward = false;
        for (int i = 0; i < parameterNames.length; i++) {
            if ("toMethodId".equals(parameterNames[i]) && ObjectUtil.isNotNull(args[i])) {
                hasForward = true;
                break;
            }
        }

        //查找转向方法
        if (hasForward) {

        }
        point.proceed();
    }
}
