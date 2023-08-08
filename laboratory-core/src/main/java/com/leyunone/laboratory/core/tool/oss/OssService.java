package com.leyunone.laboratory.core.tool.oss;

import org.apache.dubbo.common.extension.SPI;
import org.springframework.web.multipart.MultipartFile;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
@SPI
public interface OssService {

    String uploadFile(MultipartFile file);
}
