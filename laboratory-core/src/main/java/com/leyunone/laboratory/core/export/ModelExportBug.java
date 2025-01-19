package com.leyunone.laboratory.core.export;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.enums.WriteDirectionEnum;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.fill.FillConfig;
import cn.idev.excel.write.metadata.fill.FillWrapper;
import com.leyunone.laboratory.core.export.data.TestData;
import com.leyunone.laboratory.core.export.data.TestHeadData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * :)
 * 记录一次模板到处的bug fastExcel
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2025/1/7
 */
@RestController
@RequestMapping("/export")
public class ModelExportBug {

    public static void main(String[] args) throws IOException {
        ModelExportBug modelExportBug = new ModelExportBug();
        modelExportBug.modelBug();
    }

    @GetMapping("/model/bug")
    public void modelBug() throws IOException {
        Resource resource = new ClassPathResource("export-mode.xlsx");

        File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".xlsx");
        ExcelWriter excelWriter = buildExport(tempFile, resource.getInputStream());

        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig horizontalFillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        FillConfig verticalFillConfig = FillConfig.builder().forceNewRow(false).direction(WriteDirectionEnum.VERTICAL).build();

        TestHeadData testHeadData = new TestHeadData();
        testHeadData.setNoCount("1");
        testHeadData.setCreateTime("22222");
        testHeadData.setRemark("2222333");
        testHeadData.setSumCount("2");
        // 模板文件见 templateFileName 填充内容为
        excelWriter.fill(new FillWrapper(TestHeadData.class.getSimpleName(), CollectionUtil.newArrayList(testHeadData)), horizontalFillConfig, writeSheet);

        List<TestData> testDataList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            testDataList.add(TestData.builder()
                    .color("颜色")
                    .content("内容")
                    .count("count")
                    .image("imag")
                    .key("11")
                    .name("name1")
                    .no(i + ".no")
                    .otherImage("othimag")
                    .remark("12测试")
                    .testInfo("又信息")
                    .type("t")
                    .build());
        }

        excelWriter.fill(new FillWrapper(TestData.class.getSimpleName(), testDataList), verticalFillConfig, writeSheet);
        excelWriter.finish();
        FileOutputStream fos = new FileOutputStream("f://test.xlsx");
        IoUtil.copy(IoUtil.toStream(tempFile), fos);
    }


    /**
     * 导出
     *
     * @param
     * @param templatePath
     */
    public static ExcelWriter buildExport(File file,
                                          InputStream templatePath) throws IOException {
        return EasyExcel.write(file)
                .withTemplate(templatePath)
                .build();
    }
}
