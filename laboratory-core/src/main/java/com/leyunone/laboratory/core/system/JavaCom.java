package com.leyunone.laboratory.core.system;

import cn.hutool.extra.expression.engine.jexl.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * :)
 *
 * @Author pengli
 * @Date 2023/12/14 17:39
 */
public class JavaCom {

    public static void main(String[] args) throws ClassNotFoundException {
//        String mappingValue = "value > 300 ? \"重度\" : value > 200 ? \"中度\" : \"轻度\"";
        String mappingValue = "value> 1? LocalDateTime.now().toString(): \"1\" ";
        Integer valued = 100;
//        Object o = compilerMappingCode(mappingValue, valued);
        Object o = compilerMappingCode2(mappingValue, valued);
        System.out.println(o);
    }
    
    private static Object compilerMappingCode2(String mappingValue, Integer value) {
        // 创建编译参数
        MapContext context = new MapContext();
        context.set("value",value);
        context.set("date",LocalDateTime.now());
        // 创建运行环境
        Engine engine = new Engine();
        // 执行代码
        JexlExpression expression = engine.createExpression(mappingValue);
        return expression.evaluate(context);
    }
    
    private static Object compilerMappingCode(String mappingValue, Integer value) {
        //唯一短串设置编辑文件缓存
        String runPath = "";
        StringBuilder sb = new StringBuilder();
        sb.append("public class CustomMappingCompiler { public Object mapping(Integer value) { return ");
        sb.append(mappingValue);
        sb.append(";}}");
        Object result = null;
        File tempFile = new File("classpath:CustomMappingCompiler.java");
        try {
            FileWriter writer = new FileWriter(tempFile);
            writer.write(sb.toString());
            writer.close();
            runPath = tempFile.getPath();
        } catch (Exception e) {
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, runPath);

        Class<?> customMappingCompiler = null;
        try {
            customMappingCompiler = Class.forName("CustomMappingCompiler");
            Object o = customMappingCompiler.getDeclaredConstructor().newInstance();
            result = customMappingCompiler.getMethod("mapping",Integer.class).invoke(o, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
