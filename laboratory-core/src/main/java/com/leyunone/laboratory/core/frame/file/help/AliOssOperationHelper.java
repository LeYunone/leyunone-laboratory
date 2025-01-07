package com.leyunone.laboratory.core.frame.file.help;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * :)
 * 阿里云文件操作
 *
 * @Author LeYunone
 * @Date 2024/12/23 16:33
 */
@Component
public class AliOssOperationHelper {

    private static final Logger logger = LoggerFactory.getLogger(AliOssOperationHelper.class);

    @Value("${oss.endpoint:}")
    private String endpoint;
    @Value("${oss.accessKeyId:}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret:}")
    private String accessKeySecret;
    @Value("${oss.bucketName:}")
    private String bucketName;
    @Value("${oss.bucketUrl:}")
    private String bucketUrl;
    //文件前缀
    @Value("${spring.application.name:prefix}")
    private String prefix;

    /**
     * 分块上传完成获取结果
     */
    public String completePartUploadFile(String fileName, String uploadId, List<PartETag> partETags) {
        String fileKey = prefix + "/" + fileName;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest(bucketName, fileKey, uploadId,
                partETags);
        ossClient.completeMultipartUpload(request);
        ossClient.shutdown();
        return getDownloadUrl(fileKey);
    }


    /**
     * @param fileName  文件名称
     * @param is       文件流数据
     * @param uploadId oss唯一分片id
     * @param fileMd5  文件的md5值（非必传）
     * @param partNum  第几片
     * @param partSize 分片大小
     * @return
     */
    public PartETag partUploadFile(String fileName, InputStream is, String uploadId, String fileMd5, int partNum, long partSize) {
        String fileKey = prefix + "/" + fileName;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(bucketName);
        uploadPartRequest.setUploadId(uploadId);
        uploadPartRequest.setPartNumber(partNum);
        uploadPartRequest.setPartSize(partSize);
        uploadPartRequest.setInputStream(is);
        uploadPartRequest.setKey(fileKey);
//        uploadPartRequest.setMd5Digest(fileMd5);
        UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
        ossClient.shutdown();
        return uploadPartResult.getPartETag();
    }

    /**
     * 分块上传完成获取结果
     */
    public String getUploadId(String fileName) {
        String fileKey = prefix + "/" + fileName;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, fileKey);
        // 初始化分片
        InitiateMultipartUploadResult unrest = ossClient.initiateMultipartUpload(request);
        ossClient.shutdown();
        // 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个ID来发起相关的操作，如取消分片上传、查询分片上传等。
        return unrest.getUploadId();
    }


    public String getFileUrl(String name, Long expireTime) {
        String fileKey = prefix + "/" + name;

        if (ObjectUtil.isNull(expireTime)) {
            return this.getDownloadUrl(fileKey);
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + expireTime);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, fileKey, expiration);
        // 关闭OSSClient。
        ossClient.shutdown();
        return url.toString();
    }

    public void deleteFile(String fileName) {
        String fileKey = prefix + "/" + fileName;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileKey);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public String uploadFile(String name, InputStream stream) {
        String fileKey = prefix + "/" + name;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建OSSClient实例。
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(bucketName, fileKey, stream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return getDownloadUrl(fileKey);
    }

    public void cancelFile(String fileName, String uploadId) {
        String fileKey = prefix + "/" + fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 取消分片上传。
        AbortMultipartUploadRequest abortMultipartUploadRequest =
                new AbortMultipartUploadRequest(bucketName, fileKey, uploadId);
        ossClient.abortMultipartUpload(abortMultipartUploadRequest);
        ossClient.shutdown();
    }

    public List<String> batchUpload(List<BatchUpload> uploadBos) {
        List<String> paths = new ArrayList<>();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        for (BatchUpload uploadBo : uploadBos) {
            ossClient.putObject(bucketName, uploadBo.getName(), uploadBo.getStream());
            paths.add(getDownloadUrl(uploadBo.getName()));
        }
        ossClient.shutdown();
        return paths;
    }

    /**
     * 获取bucket文件的下载链接
     */
    private String getDownloadUrl(String fileKey) {
        StringBuilder url = new StringBuilder();
        //文件前缀为项目名
        url.append("https://").append(bucketUrl).append("/").append(fileKey);
        return url.toString();
    }

    public static class BatchUpload {

        private String name;

        private InputStream stream;

        public String getName() {
            return name;
        }

        public BatchUpload setName(String name) {
            this.name = name;
            return this;
        }

        public InputStream getStream() {
            return stream;
        }

        public BatchUpload setStream(InputStream stream) {
            this.stream = stream;
            return this;
        }
    }
}
