package com.leyunone.laboratory.core.concurrent.lock;

import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-04
 */
public class ZookeeperLock {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String con = "";

    private ZooKeeper zooKeeper ;

    private CountDownLatch conSuccess = new CountDownLatch(1);

    private CountDownLatch lockWathch = new CountDownLatch(1);

    private String currenNode;

    private String preNode;

    public ZookeeperLock() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(con, 2000, new Watcher() {
            /**
             * 监控节点的方法
             * @param watchedEvent
             */
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    //如果Zookeeper 监听器监听连接成功
                    conSuccess.countDown();
                }

                //如果监听到的是删除节点操作  并且这个节点与当前线程的等待锁监听的前一节点路径相同
                if(watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(preNode)){
                    lockWathch.countDown();
                }
            }
        });

        this.zooKeeper = zooKeeper;
        conSuccess.await();

        Stat exists = zooKeeper.exists("/locks", false);
        if(exists == null){
            zooKeeper.create("/locks","locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
    }

    /**
     * 加锁方法
     */
    public void lock() throws KeeperException, InterruptedException {
        if(zooKeeper == null){
            logger.error("连接异常");
        }
        //ZooDefs.Ids.OPEN_ACL_UNSAFE 完全开放这个节点下的读写     CreateMode.EPHEMERAL_SEQUENTIAL自增长的业务id
        currenNode = zooKeeper.create("/locks/" + "lock-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        List<String> children = zooKeeper.getChildren("/locks", false);
        //按自增长顺序排序
        Collections.sort(children);

        //业务id
        String ephemeral = currenNode.substring("/locks/".length());
        if(CollectionUtils.isNotEmpty(children)){
            int i = children.indexOf(ephemeral);
            if(i==-1){
                logger.error("节点异常");
            }else if (i != 0){
                //监控前一个节点 watch = true
                byte[] data = zooKeeper.getData("/locks/" + children.get(i - 1), true, null);
                preNode = "/locks/"+children.get(i-1);
                //阻塞当前线程 直到前一个节点被释放【删除】
                lockWathch.await();
                return;
            }else{
                //获得锁
                return;
            }
        }
    }

    public void unLock() throws KeeperException, InterruptedException {
        zooKeeper.delete(currenNode,1);
    }
}
