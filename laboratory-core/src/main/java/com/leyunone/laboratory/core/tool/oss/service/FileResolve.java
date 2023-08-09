package com.leyunone.laboratory.core.tool.oss.service;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/8/9 10:53
 */
@SPI
public interface FileResolve {

    String uploadFile(URL url, Integer appId, String name, InputStream stream);

}
