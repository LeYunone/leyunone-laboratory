package com.leyunone.laboratory.core.bug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-10-30
 */
//@SpringBootApplication
public class Spring注入 {

    @Autowired
    private MyLock lock;

    @Autowired
    private TestLock testLock;

    public static void main(String[] args) {
        SpringApplication.run(Spring注入.class, args);
    }

    //AutowiredAnnotationBeanPostProcessor
//    AbstractBeanFactory   isTypeMatch
//    ResolvableType
    //ConfigurationClassBeanDefinitionReader loadBeanDefinitionsForBeanMethod
}

//@Configuration
class config{

    @Bean
    public MyLock getLock(){
        return new TestLock();
    }
}

interface MyLock {
}

class TestLock implements MyLock{

}
