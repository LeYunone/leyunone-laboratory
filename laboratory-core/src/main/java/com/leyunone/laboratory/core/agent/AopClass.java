package com.leyunone.laboratory.core.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-09
 */
public class AopClass implements ClassFileTransformer {
    /**
     *
     * @param loader  定义要转换的类加载器
     * @param className 当前类名
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer 类文件格式的输入字节缓冲区（不得修改）
     * @return 一个类的字节码
     * @throws IllegalClassFormatException
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer){
        if (!className.contains("MockService")) {
            return null; //
        }

        try {
            //JavaAssist工具 进行字节码插桩
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get("com.leyunone.laboratory.core.agent.MockService");
            CtMethod personFly = cc.getDeclaredMethod("doService");

            //在目标方法前后，插入代码
            personFly.insertBefore("System.out.println('A');");
            personFly.insertAfter("System.out.println('B');");
            return cc.toBytecode();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
