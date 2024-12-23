package com.leyunone.laboratory.core.file.core.upload;

import com.leyunone.laboratory.core.file.bean.ShardUploadFileData;
import com.leyunone.laboratory.core.file.bean.ShardUploadFileResponse;
import com.leyunone.laboratory.core.file.help.LocalOperationHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    public ShardUploadFileResponse shardUploadFile(ShardUploadFileData shardUploadFileData) {
        ShardUploadFileResponse shardUploadFileResponse = localOperationHelper.shardUpload(shardUploadFileData);
        return shardUploadFileResponse;
    }

    public String uploadFile(MultipartFile file) {
        String url = null;
        try {
            url = localOperationHelper.uploadFile(file.getName(), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
