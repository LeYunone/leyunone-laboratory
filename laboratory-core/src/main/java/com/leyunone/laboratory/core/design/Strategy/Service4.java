package com.leyunone.laboratory.core.design.Strategy;

import org.springframework.stereotype.Service;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-08
 */
@Service("service4")
public class Service4 implements BaseService {
    @Override
    public String test(){
        return "test1";
    }
}
