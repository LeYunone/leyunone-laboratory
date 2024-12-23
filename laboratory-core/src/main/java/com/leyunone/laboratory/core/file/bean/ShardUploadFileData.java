package com.leyunone.laboratory.core.file.bean;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/12/23 17:23
 */
public class ShardUploadFileData implements Serializable {

    /**
     * 当前分片下标
     */
    private Integer chunkNumber;

    /**
     * 上传id
     */
    private String uploadId;

    /**
     * 分片大小
     */
    private Integer chunkSize;

    /**
     * 当前分片大小
     */
    private Integer currentChunkSize;

    /**
     * 总文件大小
     */
    private Long totalSize;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 总分片数
     */
    private Integer totalChunks;

    /**
     * 分片文件
     */
    private MultipartFile file;

    /**
     * 文件唯一标识
     */
    private String uniqueIdentifier;

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public ShardUploadFileData setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
        return this;
    }

    public String getUploadId() {
        return uploadId;
    }

    public ShardUploadFileData setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public Integer getChunkSize() {
        return chunkSize;
    }

    public ShardUploadFileData setChunkSize(Integer chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public Integer getCurrentChunkSize() {
        return currentChunkSize;
    }

    public ShardUploadFileData setCurrentChunkSize(Integer currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
        return this;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public ShardUploadFileData setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ShardUploadFileData setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public ShardUploadFileData setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
        return this;
    }

    public MultipartFile getFile() {
        return file;
    }

    public ShardUploadFileData setFile(MultipartFile file) {
        this.file = file;
        return this;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public ShardUploadFileData setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
        return this;
    }
}
