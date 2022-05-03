package xyz.leyuna.laboratory.core.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-03
 */
@SPI
public interface PersonInterface {

    void driveCar(URL url);
}
