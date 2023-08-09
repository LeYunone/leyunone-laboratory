package com.leyunone.laboratory.core.tool.oss.boost;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.ServiceException;
import com.leyunone.laboratory.core.tool.oss.service.OssService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * :)
 * AOP补强
 * @author leyunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public class OssAspect implements OssService {
    
    private final Logger logger = LoggerFactory.getLogger(OssAspect.class);

    private final OssService ossService;
    
    public OssAspect(OssService ossService){
        this.ossService = ossService;
    }

    @Override
    public String uploadFile(URL url,String name, InputStream stream) {
        if(ObjectUtil.isNull(stream)){
            throw new ServiceException("stream is empty");
        }
        String fileUrl = ossService.uploadFile(url, name, stream);
        logger.info("文件上传成功，路径:"+fileUrl);
        return fileUrl;
    }

    @Override
    public String getFileUrl(String name, Long expireTime) {
        String fileUrl = ossService.getFileUrl(name, expireTime);
        logger.info( "{} 文件路径为：{}，有效时长：{}",name,fileUrl,expireTime);
        return fileUrl;
    }

    @Override
    public void downFile(OutputStream outputStream, String objectName) {
        if(StringUtils.isBlank(objectName)) return;
        logger.info("oss down is start =/is ok");
        ossService.downFile(outputStream,objectName);
        logger.info("oss down is finished =/is ok");
    }

    @Override
    public void deleteFile(String name) {
        if(StringUtils.isBlank(name)) return;
        ossService.deleteFile(name);
    }
}

