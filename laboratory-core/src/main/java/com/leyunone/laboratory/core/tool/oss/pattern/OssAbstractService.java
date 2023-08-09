package com.leyunone.laboratory.core.tool.oss.pattern;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.leyunone.laboratory.core.tool.oss.service.OssService;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/8/9 14:20
 */
public abstract class OssAbstractService implements OssService {
    
    
    public abstract String getBucketName();
    
    @Override
    public String uploadFile(org.apache.dubbo.common.URL url,String name, InputStream stream) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(getBucketName(), name, stream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return name;
    }

    @Override
    public String getFileUrl(String name, Long expireTime) {
        if(ObjectUtil.isNull(expireTime)) {
            //默认时长
            expireTime = 30 * 60 * 1000L;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + expireTime);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(getBucketName(), name, expiration);
        // 关闭OSSClient。
        ossClient.shutdown();
        return url.toString();
    }

    @Override
    public void downFile(OutputStream outputStream, String objectName) {
        BufferedInputStream inputStream = null;
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        OSSObject ossObject = ossClient.getObject(getBucketName(), objectName);
        try {
            inputStream = new BufferedInputStream(ossObject.getObjectContent());
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            ossClient.shutdown();
        } catch (Exception e) {
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }catch (Exception e){
            }
        }
    }

    @Override
    public void deleteFile(String name) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(getBucketName(), name);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
