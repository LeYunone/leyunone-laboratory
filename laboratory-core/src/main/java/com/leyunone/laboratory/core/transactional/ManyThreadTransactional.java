package com.leyunone.laboratory.core.transactional;

import com.leyunone.laboratory.core.util.excel.ExcelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/7/14
 */
@Service
public class ManyThreadTransactional {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private final ExcelDao excelDao;

    public ManyThreadTransactional(ExcelDao excelDao) {
        this.excelDao = excelDao;
    }


    @Transactional
    public void mainThread() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i <= 9; i++) {
            CompletableFuture.runAsync(() -> {
                TransactionStatus transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
                try {
                    excelDao.save(null);
                    countDownLatch.countDown();
                    countDownLatch.await(30, TimeUnit.SECONDS);
                    platformTransactionManager.commit(transaction);
                    atomicInteger.incrementAndGet();
                } catch (Exception e) {
                    platformTransactionManager.rollback(transaction);
                }
            });
        }
        countDownLatch.await();
        //二阶段确认
        if (atomicInteger.get() == 10) {
            //操作成功
        }else{
            //补偿
        }
    }
}
