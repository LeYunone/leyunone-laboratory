package com.leyunone.laboratory.core.file.core.enhance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

    @Pointcut("execution(com.leyunone.laboratory.core.file.core.UploadFileController.**(..))")
    public void pointCut() {
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 执行文件操作需增强的情况
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around("pointCut()")
    public void enhanceMetohd(ProceedingJoinPoint proceedingJoinPoint) {

    }
}
