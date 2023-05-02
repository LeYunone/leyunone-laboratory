 # 是什么

前有神农食百草，今有刘墉毒不倒；
不过，这个项目和上面的话完全没关系 🤭， 这只是一个供我实验、种田的地方。

# 目前收录

## core

### collection包

1. **SetAPI**

   Set集合Api

2. **UniqueSet**

   Fuction接口自定义UniqueSet: 集合去重 

### concurrent包

1. **CasLock**

   自定义CAS独占锁: 自旋 - 优化 

2. **ConcurrentScene**

   模拟高并发场景

3. **ZookeeperLock**

   手写基于JDK的Zookeeper分布式锁，基于节点监听、排队...

4. **RedisLock**

   基于Redis的分布式锁

### SPI包

1. **Dubbo特殊的SPI**

   @SPI、Set方法注入的IOC，基于文档配置的AOP

2. **JDK机制的SPI**

   原生SPI，无法指定...
   
### agent包

1. **探针AOP**

  探针对指定方法进行AOP补强

2. **探针热部署**

  模拟通过探针修改已构建完成的Java程序中的Class文件
  
### openapi包

1. **百度收录**

百度站长API收录URL

2. **手机号搜索，人肉一环**

通过前3位，后2位，遍历所有可能手机号

### mq包

1. **mqtt自动装载**

mqtt-springboot自动装配

### algorithm包

1. **猴子排序**

沙雕代码

2. **睡眠排序**

沙雕代码



  
  

