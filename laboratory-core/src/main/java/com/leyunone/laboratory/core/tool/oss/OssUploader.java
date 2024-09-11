package com.leyunone.laboratory.core.tool.oss;

import lombok.Data;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/9/10 15:30
 */
@Data
public class OssUploader {

    private String accessId;

    private String policy;

    private String signature;

    private String dir;

    private String host;

    private String expire;

    private String callBack;

}
