package xyz.leyuna.laboratory.core.design.Strategy;

import org.springframework.stereotype.Service;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-08
 */
@Service("service2")
public class Service2 implements BaseService {
    @Override
    public String test(){
        return "test1";
    }
}
