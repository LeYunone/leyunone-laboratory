package com.leyunone.laboratory.core.frame.file.core.upload;

import com.leyunone.laboratory.core.frame.file.bean.ShardUploadFileData;
import com.leyunone.laboratory.core.frame.file.bean.ShardUploadFileResponse;
import com.leyunone.laboratory.core.frame.file.core.shard.UploadContext;
import com.leyunone.laboratory.core.frame.file.help.LocalOperationHelper;
import com.leyunone.laboratory.core.frame.file.help.UploadCheckHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * :)
 * 本地磁盘上传服务
 *
 * @Author LeYunone
 * @Date 2024/12/23 17:14
 */
@Service
public class LocalUploadFileService {

    private final LocalOperationHelper localOperationHelper;

    public LocalUploadFileService(LocalOperationHelper localOperationHelper) {
        this.localOperationHelper = localOperationHelper;
    }

    public List<String> uploadFile(List<MultipartFile> files) {
        List<LocalOperationHelper.BatchUpload> uploads = files.stream().map(file -> {
            LocalOperationHelper.BatchUpload batchUpload = new LocalOperationHelper.BatchUpload();
            batchUpload.setName(file.getOriginalFilename());
            try {
                batchUpload.setBytes(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return batchUpload;
        }).collect(Collectors.toList());
        return localOperationHelper.uploadFiles(uploads);
    }

    /**
     * 分片上传文件
     *
     * @param shardUploadFileData
     * @return
     */
    public ShardUploadFileResponse shardUploadFile(ShardUploadFileData shardUploadFileData) {
        ShardUploadFileResponse shardUploadFileResponse = UploadCheckHelper.checkShardFile(shardUploadFileData);
        if (!shardUploadFileResponse.isSuccess()) {
            return shardUploadFileResponse;
        }
        UploadContext.Content content = UploadContext.getUpload(shardUploadFileData.getUploadId());


        String tempPath = localOperationHelper.resoleFileTempPath(shardUploadFileData.getUniqueIdentifier());
        int currentChunkNo = shardUploadFileData.getChunkNumber();
        int totalChunks = shardUploadFileData.getTotalChunks();
        String uploadId = shardUploadFileData.getUploadId();
        //字节流转换
        Set<Integer> parts = content.getParts();
        boolean merge = false;
        //分片上传
        //非已上传的分片
        if (!parts.contains(currentChunkNo)) {
            //上传分片
            boolean success = localOperationHelper.uploadSlice(shardUploadFileData, tempPath);
            if (success) {
                parts.add(currentChunkNo);
                //如果是最后一个子分片，将合并线程放开
                if (currentChunkNo != totalChunks && parts.size() == totalChunks) {
                    merge = true;
                }
            } else {
                shardUploadFileResponse.buildError("upload file...");
                return shardUploadFileResponse;
            }
        }
        //分片编号等于总片数的时候合并文件,如果符合条件则合并文件，否则继续等待
        if (currentChunkNo == totalChunks) {
            //合并文件
            if (parts.size() != totalChunks) {
                //挂起 等待小分片上传完毕
            } else {
                merge = true;
            }

        }
        content.setParts(parts);
        UploadContext.setCache(uploadId, content);
        if (merge) {
            //最后一个分支上传完成
            try {
                String url = localOperationHelper.mergeSliceFile(tempPath, content.getFileKey(), shardUploadFileData.getFile().getOriginalFilename());
                shardUploadFileResponse.setSuccess(true);
                shardUploadFileResponse.setFilePath(url);
                shardUploadFileResponse.setTotalSize(shardUploadFileData.getTotalSize());
                shardUploadFileResponse.setFileName(shardUploadFileData.getFile().getOriginalFilename());
                shardUploadFileResponse.setIdentifier(shardUploadFileData.getUniqueIdentifier());
            } catch (Exception e) {
                e.printStackTrace();
                shardUploadFileResponse.buildError("upload file...");
            } finally {
                /**
                 * 如果是最后一个该文件的上传请求就清空缓存
                 */
                UploadContext.removeCache(uploadId);
                UploadContext.removeId(shardUploadFileData.getUniqueIdentifier());
                localOperationHelper.deleteSliceTemp(tempPath);
            }
        }
        return shardUploadFileResponse;
    }

    public String uploadFile(MultipartFile file) {
        String url = null;
        try {
            url = localOperationHelper.uploadFile(file.getOriginalFilename(), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public boolean deleteFile(String... files) {
        localOperationHelper.deleteFile(files);
        return true;
    }
}
