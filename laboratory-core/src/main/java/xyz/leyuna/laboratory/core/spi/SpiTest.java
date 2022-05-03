package xyz.leyuna.laboratory.core.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-03
 *
 * 测试SPI机制
 */
public class SpiTest {

    public static void main(String[] args) {

        ServiceLoader<CarInterface> load = ServiceLoader.load(CarInterface.class);
        Iterator<CarInterface> iterator = load.iterator();
        while(iterator.hasNext()){
            iterator.next().didi();
        }
    }
}
