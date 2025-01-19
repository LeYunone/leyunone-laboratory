package com.leyunone.laboratory.core.util.excel;

import java.util.List;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author leyuna
 */
@Slf4j
public class ExcelListen<T> implements ReadListener<T> {

    private static final int BATCH_COUNT = 100;
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private ExcelDao excelDao;

    public ExcelListen(ExcelDao excelDao) {
        this.excelDao = excelDao;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        excelDao.save(cachedDataList);
        log.info("存储数据库成功！");
    }
}
