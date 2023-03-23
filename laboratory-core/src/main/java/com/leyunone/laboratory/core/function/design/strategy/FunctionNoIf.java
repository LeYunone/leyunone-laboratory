package com.leyunone.laboratory.core.function.design.strategy;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-08
 */
public class FunctionNoIf {

    private Map<String, Function<String, String>> map = new HashMap<>();
    @Autowired
    private Service1 service1;
    @Autowired
    private Service1 service2;
    @Autowired
    private Service1 service3;
    @Autowired
    private Service1 service4;

    {
        map.put("1", name -> service1.test(name));
        map.put("2", name -> service2.test(name));
        map.put("3", name -> service3.test(name));
        map.put("4", name -> service4.test(name));
    }

    public static void main(String[] args) {
        //从逻辑分派Dispatcher中获得业务逻辑代码，result变量是一段lambda表达式
        FunctionNoIf functionNoIf = new FunctionNoIf();
        Function<String, String> function = functionNoIf.map.get(1);
        String name = "入参";
        String result = "";
        if (function != null) {
            //执行这段表达式获得String类型的结果
            result = function.apply("name");
        }
    }
}
