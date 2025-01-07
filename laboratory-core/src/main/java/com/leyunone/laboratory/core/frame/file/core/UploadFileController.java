package com.leyunone.laboratory.core.frame.file.core;

import com.leyunone.laboratory.core.bean.DataResponse;
import com.leyunone.laboratory.core.frame.file.bean.ShardUploadFileData;
import com.leyunone.laboratory.core.frame.file.bean.ShardUploadFileResponse;
import com.leyunone.laboratory.core.frame.file.bean.StsTokenResponse;
import com.leyunone.laboratory.core.frame.file.core.upload.AliOssUploadFileService;
import com.leyunone.laboratory.core.frame.file.core.upload.LocalUploadFileService;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/common/file/upload")
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
    public DataResponse<String> ossUpload(MultipartFile file, @RequestParam(required = false) String toMethodId) {
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
    public DataResponse<List<String>> ossUpload(List<MultipartFile> files, @RequestParam(required = false) String toMethodId) {
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
    public DataResponse<String> localUpload(MultipartFile file, @RequestParam(required = false) String toMethodId) {
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
    public DataResponse<List<String>> localUpload(List<MultipartFile> files, @RequestParam(required = false) String toMethodId) {
        List<String> urls = localUploadFileService.uploadFile(files);
        return DataResponse.of(urls);
    }


    /**
     * 服务端代理的阿里云分片上传 获取上传id
     */
    @PostMapping(value = "/oss/proxy/shard/requestUpload")
    public DataResponse<String> ossProxyShardRequest(ShardUploadFileData shardUploadFileData, @RequestParam(required = false) String toMethodId) {
        String upload = aliOssUploadFileService.requestUpload(shardUploadFileData);
        return DataResponse.of(upload);
    }

    /**
     * 服务端代理的阿里云分片上传
     */
    @PostMapping(value = "/oss/proxy/shard/upload", headers = "content-type=multipart/form-data")
    public DataResponse<ShardUploadFileResponse> ossProxyShardUpload(ShardUploadFileData shardUploadFileData, @RequestParam(required = false) String toMethodId) {
        ShardUploadFileResponse shardUploadFileResponse = aliOssUploadFileService.shardUpload(shardUploadFileData);
        return DataResponse.of(shardUploadFileResponse);
    }

    /**
     * 客户端直传的阿里云上传
     * sts签名
     */
    @PostMapping(value = "/oss/passThrough/sts", headers = "content-type=multipart/form-data")
    public DataResponse<StsTokenResponse> ossPassThroughUpload(@RequestParam(required = false) String toMethodId) {
        return DataResponse.of();
    }

    /**
     * 本地磁盘的分片上传
     *
     * @return
     */
    @PostMapping(value = "/local/shardUpload", headers = "content-type=multipart/form-data")
    public DataResponse<ShardUploadFileResponse> localShardUpload(ShardUploadFileData shardUploadFileData, @RequestParam(required = false) String toMethodId) {
        ShardUploadFileResponse shardUploadFileResponse = localUploadFileService.shardUploadFile(shardUploadFileData);
        return DataResponse.of(shardUploadFileResponse);
    }

    /**
     * 阿里云删除文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/oss/deleteFile")
    public DataResponse<?> ossDeleteFile(String file) {
        aliOssUploadFileService.deleteFile(file);
        return DataResponse.of();
    }

    /**
     * 本地磁盘删除文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/local/deleteFile")
    public DataResponse<?> localDeleteFile(List<String> file) {
        localUploadFileService.deleteFile(file.toArray(new String[]{}));
        return DataResponse.of();
    }

    /**
     * 申请获取oss文件的路径
     *
     * @param file
     * @return
     */
    @GetMapping(value = "/oss/accessFile")
    public DataResponse<String> ossAccessFile(String file, Long expireTime) {
        String url = aliOssUploadFileService.getUrl(file, expireTime);
        return DataResponse.of(url);
    }

    /**
     * 从本地磁盘中下载文件
     *
     * @param file
     * @return
     */
    @GetMapping(value = "/local/downFile")
    public DataResponse<?> localDownFile(String file) {
        return DataResponse.of();
    }
}
