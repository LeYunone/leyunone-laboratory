package com.leyunone.laboratory.core.frame.file.core.upload;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.model.PartETag;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.laboratory.core.frame.file.bean.ShardUploadFileData;
import com.leyunone.laboratory.core.frame.file.bean.ShardUploadFileResponse;
import com.leyunone.laboratory.core.frame.file.core.shard.UploadContext;
import com.leyunone.laboratory.core.frame.file.help.AliOssOperationHelper;
import com.leyunone.laboratory.core.frame.file.help.UploadCheckHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 * 阿里云上传服务
 *
 * @Author LeYunone
 * @Date 2024/10/23 14:15
 */
@Service
public class AliOssUploadFileService {

    private final AliOssOperationHelper aliOssOperationHelper;

    public AliOssUploadFileService(AliOssOperationHelper aliOssOperationHelper) {
        this.aliOssOperationHelper = aliOssOperationHelper;
    }

    public String requestUpload(ShardUploadFileData shardUploadFileData) {
        String uploadId = UploadContext.getId(shardUploadFileData.getUniqueIdentifier());
        String fileKey = "";
        if (StringUtils.isNotBlank(uploadId)) {
            //该文件已经在上传流程中
            UploadContext.Content upload = UploadContext.getUpload(uploadId);
            if (ObjectUtil.isNotNull(upload)) {
                fileKey = upload.getFileKey();
            } else {
                //两个缓存有延时问题，删除缓存
                UploadContext.removeId(shardUploadFileData.getUniqueIdentifier());
                /**
                 * 迭代判断
                 */
                return this.requestUpload(shardUploadFileData);
            }
        } else {
            try {
                uploadId = aliOssOperationHelper.getUploadId(shardUploadFileData.getFilename());
                UploadContext.Content content = new UploadContext.Content();
                content.setPartETags(new HashMap<>());
                content.setFileKey(shardUploadFileData.getFilename());
                content.setUploadId(uploadId);
                UploadContext.setCache(uploadId, content);
                UploadContext.setId(shardUploadFileData.getUniqueIdentifier(), uploadId);
            } finally {
            }
        }
        return uploadId;
    }

    public ShardUploadFileResponse shardUpload(ShardUploadFileData shardUploadFileData) {
        ShardUploadFileResponse shardUploadFileResponse = UploadCheckHelper.checkShardFile(shardUploadFileData);
        if (!shardUploadFileResponse.isSuccess()) {
            return shardUploadFileResponse;
        }
        UploadContext.Content content = UploadContext.getUpload(shardUploadFileData.getUploadId());


        int currentChunkNo = shardUploadFileData.getChunkNumber();
        int totalChunks = shardUploadFileData.getTotalChunks();
        String ossSlicesId = shardUploadFileData.getUploadId();
        //字节流转换
        Map<Integer, PartETag> partETags = content.getPartETags();
        boolean merge = false;
        //分片上传
        if (!partETags.containsKey(currentChunkNo)) {
            //每次上传分片之后，OSS的返回结果会包含一个PartETag
            PartETag partETag = null;
            try {
                partETag = aliOssOperationHelper.partUploadFile(content.getFileKey(), shardUploadFileData.getFile().getInputStream(), ossSlicesId,
                        shardUploadFileData.getUniqueIdentifier(), currentChunkNo, shardUploadFileData.getFile().getSize());
            } catch (IOException e) {
                e.printStackTrace();
                shardUploadFileResponse.buildError("upload file...");
                return shardUploadFileResponse;
            }
            partETags.put(currentChunkNo, partETag);
            //如果是最后一个子分片，将合并线程放开
            if (currentChunkNo != totalChunks && partETags.size() == totalChunks) {
                merge = true;
            }
        }
        //分片编号等于总片数的时候合并文件,如果符合条件则合并文件，否则继续等待
        if (currentChunkNo == totalChunks) {
            //合并文件
            if (partETags.size() != totalChunks) {
                //挂起 等待小分片上传完毕
            } else {
                merge = true;
            }
        }
        content.setPartETags(partETags);
        UploadContext.setCache(ossSlicesId, content);
        if (merge) {
            //合并文件
            try {
                String url = aliOssOperationHelper.completePartUploadFile(content.getFileKey(), ossSlicesId, new ArrayList<>(partETags.values()));
                shardUploadFileResponse.setSuccess(true);
                shardUploadFileResponse.setFilePath(url);
                shardUploadFileResponse.setTotalSize(shardUploadFileData.getTotalSize());
                shardUploadFileResponse.setFileName(shardUploadFileData.getFile().getOriginalFilename());
                shardUploadFileResponse.setIdentifier(shardUploadFileData.getUniqueIdentifier());
            } catch (Exception e) {
                e.printStackTrace();
                shardUploadFileResponse.setSuccess(false);
            } finally {
                /**
                 * 如果是最后一个该文件的上传请求就清空缓存
                 */
                UploadContext.removeCache(shardUploadFileData.getUploadId());
                UploadContext.removeId(shardUploadFileData.getUniqueIdentifier());
            }
        }
        return shardUploadFileResponse;
    }

    public List<String> uploadFile(List<MultipartFile> files) {
        List<AliOssOperationHelper.BatchUpload> uploads = files.stream().map(file -> {
            AliOssOperationHelper.BatchUpload batchUpload = new AliOssOperationHelper.BatchUpload();
            batchUpload.setName(file.getOriginalFilename());
            try {
                batchUpload.setStream(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return batchUpload;
        }).collect(Collectors.toList());
        return aliOssOperationHelper.batchUpload(uploads);
    }

    public String uploadFile(MultipartFile file) {
        String url = null;
        try {
            url = aliOssOperationHelper.uploadFile(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 文件路径
     *
     * @param file
     * @return
     */
    public boolean deleteFile(String file) {
        aliOssOperationHelper.deleteFile(file);
        return true;
    }

    public String getUrl(String url, Long expireTime) {
        return aliOssOperationHelper.getFileUrl(url, expireTime);
    }

}
