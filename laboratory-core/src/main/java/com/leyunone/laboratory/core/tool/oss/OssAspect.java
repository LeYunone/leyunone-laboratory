package com.leyunone.laboratory.core.tool.oss;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public class OssAspect implements OssService{

    private OssService ossService;
    
    public OssAspect(OssService ossService){
        this.ossService = ossService;
    }


    @Override
    public String uploadFile(MultipartFile file) {
        if(ObjectUtil.isNull(file)){
            
        }
        return ossService.uploadFile(file);
    }
}

