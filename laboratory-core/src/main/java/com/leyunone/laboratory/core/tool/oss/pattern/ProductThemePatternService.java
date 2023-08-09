package com.leyunone.laboratory.core.tool.oss.pattern;


import com.leyunone.laboratory.core.tool.oss.service.OssService;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/8/9 18:00
 */
public class ProductThemePatternService extends OssAbstractService implements OssService {

    private static final String BUCKET_NAME = "product-theme";
    
    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }
}
