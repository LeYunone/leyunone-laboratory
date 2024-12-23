package com.leyunone.laboratory.core.file.core;

import com.leyunone.laboratory.core.bean.DataResponse;
import com.leyunone.laboratory.core.file.bean.ShardUploadFileData;
import com.leyunone.laboratory.core.file.bean.ShardUploadFileResponse;
import com.leyunone.laboratory.core.file.core.upload.AliOssUploadFileService;
import com.leyunone.laboratory.core.file.core.upload.LocalUploadFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * :)
 * 上传文件
 *
 * @Author LeYunone
 * @Date 2024/10/18 15:18
 */
@RestController
@RequestMapping("/business/file/upload")
public class UploadFileController {

    @Resource
    private AliOssUploadFileService aliOssUploadFileService;
    @Resource
    private LocalUploadFileService localUploadFileService;

    /**
     * 简单的表单单文件OSS上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/oss/easyForm", headers = "content-type=multipart/form-data")
    public DataResponse<String> ossUpload(MultipartFile file) {
        String url = aliOssUploadFileService.uploadFile(file);
        return DataResponse.of(url);
    }

    /**
     * 简单的表单多文件OSS上传
     *
     * @param files
     * @return
     */
    @PostMapping(value = "/oss/easyForms", headers = "content-type=multipart/form-data")
    public DataResponse<List<String>> ossUpload(List<MultipartFile> files) {
        List<String> urls = aliOssUploadFileService.uploadFile(files);
        return DataResponse.of(urls);
    }

    /**
     * 简单的表单单文件本地上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/local/easyForm", headers = "content-type=multipart/form-data")
    public DataResponse<String> localUpload(MultipartFile file) {
        String url = localUploadFileService.uploadFile(file);
        return DataResponse.of(url);
    }

    /**
     * 简单的表单多文件本地上传
     *
     * @param files
     * @return
     */
    @PostMapping(value = "/local/easyForms", headers = "content-type=multipart/form-data")
    public DataResponse<List<String>> localUpload(List<MultipartFile> files) {
        List<String> urls = localUploadFileService.uploadFile(files);
        return DataResponse.of(urls);
    }

    /**
     * 服务端代理的阿里云分片上传
     */
    @PostMapping(value = "/oss/proxy/shardUpload", headers = "content-type=multipart/form-data")
    public DataResponse<ShardUploadFileResponse> ossProxyShardUpload() {
        return DataResponse.of();
    }

    /**
     * 客户端直传的阿里云分片上传
     */
    @PostMapping(value = "/oss/passThrough/shardUpload", headers = "content-type=multipart/form-data")
    public DataResponse<ShardUploadFileResponse> ossPassThroughShardUpload() {
        return DataResponse.of();
    }

    /**
     * 本地磁盘的分片上传
     *
     * @return
     */
    @PostMapping(value = "/local/shardUpload", headers = "content-type=multipart/form-data")
    public DataResponse<ShardUploadFileResponse> localShardUpload(ShardUploadFileData shardUploadFileData) {
        ShardUploadFileResponse shardUploadFileResponse = localUploadFileService.shardUploadFile(shardUploadFileData);
        return DataResponse.of(shardUploadFileResponse);
    }

}
