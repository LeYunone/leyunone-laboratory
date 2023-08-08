package com.leyunone.laboratory.core.tool.oss;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


/**
 * :)
 * 通过SPI机制去定向控制执行oss上传桶
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public class OssUtils {

    public static void main(String[] args) {
        uploadFile(OssTypeEnum.IMAGES,null);
    }

    public static String uploadFile(OssTypeEnum typeEnum,MultipartFile file) {
        ExtensionLoader<OssService> extensionLoader = ExtensionLoader.getExtensionLoader(OssService.class);
        return extensionLoader.getExtension(typeEnum.getType()).uploadFile(file);
    }
}
