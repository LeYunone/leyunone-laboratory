package com.leyunone.laboratory.core.tool.oss.pattern;


import com.leyunone.laboratory.core.tool.oss.service.OssService;

/**
 * :)
 *
 * @author leyunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public class FilePatternService extends OssAbstractService implements OssService {

    private static final String BUCKET_NAME = "file";

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }
}
