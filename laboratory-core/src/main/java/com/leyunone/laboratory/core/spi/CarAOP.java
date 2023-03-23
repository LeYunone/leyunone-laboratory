package com.leyunone.laboratory.core.spi;

import org.apache.dubbo.common.URL;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-03
 */
public class CarAOP implements CarInterface{

    private CarInterface carInterface;

    public CarAOP(CarInterface carInterface){
        this.carInterface = carInterface;
    }

    @Override
    public void didi(URL url) {
        System.out.println("aop加强");
        carInterface.didi(url);
        System.out.println("aop加强完成");
    }
}
