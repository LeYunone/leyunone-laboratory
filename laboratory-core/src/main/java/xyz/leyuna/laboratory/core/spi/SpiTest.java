package xyz.leyuna.laboratory.core.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-03
 * <p>
 * 测试SPI机制
 */
public class SpiTest {

    public static void main(String[] args) {

//        ServiceLoader<CarInterface> load = ServiceLoader.load(CarInterface.class);
//        Iterator<CarInterface> iterator = load.iterator();
//        while(iterator.hasNext()){
//            iterator.next().didi();
//        }

        //Dubbo SPI AOP
//        ExtensionLoader<CarInterface> extensionLoader = ExtensionLoader.getExtensionLoader(CarInterface.class);
//        CarInterface red = extensionLoader.getExtension("red");
//        red.didi();

        //Dubbo SPI IOC
        ExtensionLoader<PersonInterface> extensionLoader = ExtensionLoader.getExtensionLoader(PersonInterface.class);
        PersonInterface my = extensionLoader.getExtension("my");
        Map<String,String> map = new HashMap<>();
        map.put("carType","red");
        URL url = new URL("","",0,map);
        my.driveCar(url);
    }
}
