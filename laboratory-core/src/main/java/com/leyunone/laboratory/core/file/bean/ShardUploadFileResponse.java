package com.leyunone.laboratory.core.file.bean;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/12/23 17:29
 */
public class ShardUploadFileResponse {

    private String uploadId;

    private boolean success;

    private String filePath;

    private String fileName;

    private String identifier;

    private Long totalSize;

    private Integer chunkNumber;

    private String errorMsg;

    public void buildError(String errorMsg) {
        setErrorMsg(errorMsg);
        setSuccess(false);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ShardUploadFileResponse setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public String getUploadId() {
        return uploadId;
    }

    public ShardUploadFileResponse setUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ShardUploadFileResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ShardUploadFileResponse setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ShardUploadFileResponse setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ShardUploadFileResponse setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public ShardUploadFileResponse setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
        return this;
    }

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public ShardUploadFileResponse setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
        return this;
    }
}
