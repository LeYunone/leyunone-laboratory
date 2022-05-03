package xyz.leyuna.laboratory.core.spi;

import org.apache.dubbo.common.URL;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-03
 */
public class RedCar implements CarInterface {
    @Override
    public void didi(URL url) {
        System.out.println("RedCar didi");
    }
}
