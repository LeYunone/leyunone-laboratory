package com.leyunone.laboratory.core.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-03
 */
@SPI(value = "red")
public interface CarInterface {

    @Adaptive("carType")
    void didi(URL url);
}
