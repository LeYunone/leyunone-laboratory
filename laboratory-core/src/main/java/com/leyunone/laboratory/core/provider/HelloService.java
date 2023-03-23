package com.leyunone.laboratory.core.provider;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-01
 */
@DubboService
public class HelloService {

    public String hellow(){
        return "hellow word";
    }
}
