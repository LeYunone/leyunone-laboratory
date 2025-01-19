package com.leyunone.laboratory.core.util.excel;

import cn.idev.excel.EasyExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-10-31
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException {
        ExcelDao excelDao = new ExcelDaoImpl();
        ExcelListen<ExcelData> excelListen = new ExcelListen(excelDao);
        EasyExcel.read(new FileInputStream(""),ExcelData.class,excelListen);
    }
}
