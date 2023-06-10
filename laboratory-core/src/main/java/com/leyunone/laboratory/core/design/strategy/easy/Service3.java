package com.leyunone.laboratory.core.design.strategy.easy;

import org.springframework.stereotype.Service;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-08
 */
@Service("service3")
public class Service3 implements BaseService {
    @Override
    public String test(){
        return "test1";
    }
}
