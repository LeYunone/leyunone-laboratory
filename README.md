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
