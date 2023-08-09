package com.leyunone.laboratory.core.tool.oss.service;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * :)
 *
 * @author leyunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
@SPI
public interface OssService {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String ENDPOINT = "https://oss-cn-shenzhen.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    String ACCESS_KEY_ID = "?";
    String ACCESS_KEY_SECRET = "?";

    @Adaptive("ossPattern")
    String uploadFile( URL url,String name, InputStream stream);

    String getFileUrl(String name, Long expireTime);

    void downFile(OutputStream outputStream, String objectName);

    void deleteFile(String name);

}
