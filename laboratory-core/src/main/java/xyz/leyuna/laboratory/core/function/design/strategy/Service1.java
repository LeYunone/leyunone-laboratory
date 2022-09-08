package xyz.leyuna.laboratory.core.function.design.strategy;

import org.springframework.stereotype.Service;
import xyz.leyuna.laboratory.core.design.Strategy.BaseService;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-08
 */
@Service("service1")
public class Service1 {

    public String test(String name){
        return name;
    }
}