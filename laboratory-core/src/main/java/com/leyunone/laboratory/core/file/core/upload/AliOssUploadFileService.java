package com.leyunone.laboratory.core.file.core.upload;

import com.leyunone.laboratory.core.file.help.AliOssOperationHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *  阿里云上传服务
 * @Author LeYunone
 * @Date 2024/10/23 14:15
 */
@Service
public class AliOssUploadFileService {

    private final AliOssOperationHelper aliOssOperationHelper;

    public AliOssUploadFileService(AliOssOperationHelper aliOssOperationHelper) {
        this.aliOssOperationHelper = aliOssOperationHelper;
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
            url = aliOssOperationHelper.uploadFile(file.getName(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
