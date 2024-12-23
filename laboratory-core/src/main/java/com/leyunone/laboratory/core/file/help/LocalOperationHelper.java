package com.leyunone.laboratory.core.file.help;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.laboratory.core.file.bean.ShardUploadFileData;
import com.leyunone.laboratory.core.file.bean.ShardUploadFileResponse;
import com.leyunone.laboratory.core.file.core.shard.UploadContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * :)
 * 本地磁盘文件操作
 *
 * @Author LeYunone
 * @Date 2024/12/23 17:15
 */
@Component
public class LocalOperationHelper {

    private final Logger logger = LoggerFactory.getLogger(LocalOperationHelper.class);

    //文件路径
    @Value("${local.fileAddress:}")
    private String fileAddress;
    //下载线程
    @Value("${local.downloadThread:4}")
    private int downThread;
    //文件前缀
    @Value("${spring.application.name:prefix}")
    private String prefix;

    /**
     * 临时目录
     *
     * @param md5
     * @return
     */
    private String resoleFileTempPath(String md5) {
        String tempPath = fileAddress + "/" + md5 + "/";
        File tempFile = new File(tempPath);
        if (tempFile.exists()) {
            return tempPath;
        }
        boolean mkdirs = tempFile.mkdirs();
        return mkdirs ? tempPath : null;
    }

    public ShardUploadFileResponse shardUpload(ShardUploadFileData shardUploadFileData) {
        ShardUploadFileResponse shardUploadFileResponse = new ShardUploadFileResponse();
        shardUploadFileResponse.setSuccess(false);
        //一般规则：本次文件的MD5码
        String md5 = shardUploadFileData.getUniqueIdentifier();
        if (StringUtils.isBlank(md5)) {
            shardUploadFileResponse.setErrorMsg("uniqueIdentifier is empty");
            return shardUploadFileResponse;
        }

        //必须求出缓存中的PartETags，在分片合成文件中需要以此为依据，合并文件返回最终地址
        UploadContext.Content content = UploadContext.getUpload(shardUploadFileData.getUploadId());
        if (ObjectUtil.isNull(content)) {
            shardUploadFileResponse.setErrorMsg("file has expiration...");
            return shardUploadFileResponse;
        }

        MultipartFile file = shardUploadFileData.getFile();
        String tempPath = resoleFileTempPath(md5);
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
            boolean success = this.uploadSlice(shardUploadFileData, tempPath);
            if (success) {
                parts.add(currentChunkNo);
                //如果是最后一个子分片，将合并线程放开
                if (currentChunkNo != totalChunks && parts.size() == totalChunks) {
                    merge = true;
                }
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
                String url = this.mergeSliceFile(tempPath, content.getFileKey(), file.getOriginalFilename());
                shardUploadFileResponse.setSuccess(true);
                shardUploadFileResponse.setFilePath(url);
                shardUploadFileResponse.setTotalSize(shardUploadFileData.getTotalSize());
                shardUploadFileResponse.setFileName(file.getOriginalFilename());
                shardUploadFileResponse.setIdentifier(md5);
            } catch (Exception e) {
                e.printStackTrace();
                shardUploadFileResponse.setSuccess(false);
            } finally {
                /**
                 * 如果是最后一个该文件的上传请求就清空缓存
                 */
                UploadContext.removeCache(uploadId);
                UploadContext.removeId(md5);
                this.deleteSliceTemp(tempPath);
            }
        }
        return shardUploadFileResponse;
    }

    private boolean uploadSlice(ShardUploadFileData shardUploadFileData, String tempPath) {
        boolean success = true;
        //开始进行切片化上传
        File sliceFile = new File(tempPath + shardUploadFileData.getChunkNumber());
        //如果这个片在历史中已经完成，则跳过 双重校验
        if (!sliceFile.exists()) {
            FileOutputStream fos = null;
            InputStream inputStream = null;
            try {
                fos = new FileOutputStream(sliceFile);
                //本次上传文件
                inputStream = shardUploadFileData.getFile().getInputStream();
                //写入文件
                IOUtils.copy(inputStream, fos);
            } catch (Exception e) {
                success = false;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
        return success;
    }

    /**
     * 合并分片
     *
     * @param tempPath
     * @param fileName
     */
    private String mergeSliceFile(String tempPath, String folderName, String fileName) {
        //分片文件的临时目录
        File sliceFile = new File(tempPath);
        //本次文件的保存位置
        folderName = fileAddress + "/" + folderName;
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String savePath = folderName + fileName;
        File thisFile = new File(savePath);
        //所有分片
        File[] files = sliceFile.listFiles();
        //按照1 2 3 4 排序，有序写入
        Arrays.sort(files, Comparator.comparing(o -> Integer.parseInt(o.getName())));
        RandomAccessFile randomAccessFile = null;
        try {
            //使用RandomAccessFile 达到追加插入， 也可以使用Inputstream的Skip方法跳过已读过的
            randomAccessFile = new RandomAccessFile(thisFile, "rw");
            byte[] buffer = new byte[1024];
            for (File file : files) {
                RandomAccessFile randomAccessFileReader = new RandomAccessFile(file, "r");
                int len;
                while ((len = randomAccessFileReader.read(buffer)) != -1) {
                    //追加写入
                    randomAccessFile.write(buffer, 0, len);
                }
                randomAccessFileReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return savePath;
    }


    /**
     * 删除临时目录
     *
     * @param tempPath
     */
    private void deleteSliceTemp(String tempPath) {
        File file = new File(tempPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File cfile : files) {
                cfile.delete();
            }
            file.delete();
        }
    }


    /**
     * 直接的文件上传
     *
     * @param name
     * @param bytes
     * @return
     */
    public String uploadFile(String name, byte[] bytes) {
        List<String> paths = new ArrayList<>();
        BatchUpload batchUpload = new BatchUpload();
        batchUpload.setName(name);
        batchUpload.setBytes(bytes);
        return CollectionUtil.getFirst(this.uploadFiles(CollectionUtil.newArrayList(batchUpload)));
    }


    /**
     * 直接的文件上传
     *
     * @param batchUploads
     * @return
     */
    public List<String> uploadFiles(List<BatchUpload> batchUploads) {
        List<String> paths = new ArrayList<>();
        for (BatchUpload batchUpload : batchUploads) {
            try {
                // 构建上传路径
                Path uploadPath = Paths.get(fileAddress);

                // 如果目录不存在则创建
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 将文件写入磁盘
                Path filePath = uploadPath.resolve(batchUpload.getName());
                Files.write(filePath, batchUpload.getBytes());
                paths.add(filePath.toAbsolutePath().toString());
            } catch (IOException e) {
                logger.error("file upload error,name:{},error:{}", batchUpload.getName(), e.getMessage());
            }
        }
        return paths;
    }

    public void downloadFileConcurrently(String filePath, HttpServletResponse response) {
        ExecutorService executor = Executors.newFixedThreadPool(downThread);

        try (FileInputStream in = new FileInputStream(filePath);
             OutputStream out = response.getOutputStream()) {

            long fileSize = in.getChannel().size();
            long chunkSize = fileSize / downThread;

            File file = new File(filePath);
            String fileName = file.getName();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            for (int i = 0; i < downThread; i++) {
                long start = i * chunkSize;
                long end = (i == downThread - 1) ? fileSize : (i + 1) * chunkSize;

                executor.execute(() -> {
                    try {
                        byte[] buffer = new byte[4096];
                        in.getChannel().position(start);
                        while (in.getChannel().position() < end) {
                            int bytesRead = in.read(buffer);
                            if (bytesRead == -1) {
                                break;
                            }
                            out.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            executor.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接删除文件
     *
     * @param filePaths
     * @return
     */
    public boolean deleteFile(String... filePaths) {
        for (String pathStr : filePaths) {
            Path path = Paths.get(pathStr);
            try {
                Files.walk(path)
                        // 以相反顺序处理以避免目录非空的情况
                        .sorted((p1, p2) -> -p1.compareTo(p2))
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                logger.error("Unable to delete: {}", e.getMessage());
                            }
                        });
            } catch (IOException e) {
                logger.error("Unable to access path: {}", path);

            }
        }
        return true;
    }

    /**
     * 分片请求上传id
     *
     * @param uniqueIdentifier 文件的唯一标识
     * @return
     */
    public UploadContext.Content requestUploadId(String uniqueIdentifier) {
        String uploadId = UUID.randomUUID().toString();
        UploadContext.Content content = new UploadContext.Content();
        content.setPartETags(new HashMap<>());
        content.setFileKey(prefix);
        content.setUploadId(uploadId);
        UploadContext.setCache(uploadId, content);
        UploadContext.setId(uniqueIdentifier, uploadId);
        return content;
    }

    public static class BatchUpload {

        private String name;

        private byte[] bytes;

        public String getName() {
            return name;
        }

        public BatchUpload setName(String name) {
            this.name = name;
            return this;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public BatchUpload setBytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }
    }
}
