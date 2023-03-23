package com.leyunone.laboratory.core.spi;

import org.apache.dubbo.common.URL;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-03
 */
public class My implements PersonInterface{

    private CarInterface carInterface;

    public void setCarInterface(CarInterface carInterface){
        this.carInterface = carInterface;
    }

    @Override
    public void driveCar(URL url) {
        System.out.println("警告，我开车来了");
        carInterface.didi(url);
    }
}
