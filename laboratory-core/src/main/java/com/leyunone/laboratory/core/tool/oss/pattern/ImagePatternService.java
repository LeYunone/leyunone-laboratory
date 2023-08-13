package com.leyunone.laboratory.core.tool.oss.pattern;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.leyunone.laboratory.core.tool.oss.service.OssService;
import org.apache.dubbo.common.URL;

import java.io.InputStream;


/**
 * :)
 *
 * @author leyunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public class ImagePatternService extends OssAbstractService implements OssService {

    private static final String BUCKET_NAME = "image";

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }

    /**
     * 图片上传后获得路径
     * @param name
     * @param stream
     * @return
     */
    @Override
    public String uploadFile(URL url, String name, InputStream stream) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(getBucketName(), name, stream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return this.getFileUrl(name, null);
    }

    /**
     * 图片永久路径
     * @param name
     * @param expireTime
     * @return
     */
    @Override
    public String getFileUrl(String name, Long expireTime) {
        String imgPath = "https://my-image.oss-cn-shenzhen.aliyuncs.com/";
        return imgPath + name;
    }
}
