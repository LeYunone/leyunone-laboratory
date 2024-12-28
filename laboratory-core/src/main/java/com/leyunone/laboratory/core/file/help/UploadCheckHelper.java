package com.leyunone.laboratory.core.file.help;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.laboratory.core.file.bean.ShardUploadFileData;
import com.leyunone.laboratory.core.file.bean.ShardUploadFileResponse;
import com.leyunone.laboratory.core.file.core.shard.UploadContext;
import org.apache.commons.lang3.StringUtils;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/12/24 11:29
 */
public class UploadCheckHelper {

    public static ShardUploadFileResponse checkShardFile(ShardUploadFileData shardUploadFileData) {
        ShardUploadFileResponse shardUploadFileResponse = new ShardUploadFileResponse();
        shardUploadFileResponse.setChunkNumber(shardUploadFileData.getChunkNumber());
        shardUploadFileResponse.setUploadId(shardUploadFileData.getUploadId());
        shardUploadFileResponse.setSuccess(true);
        //一般规则：本次文件的MD5码
        String md5 = shardUploadFileData.getUniqueIdentifier();
        if (StringUtils.isBlank(md5)) {
            shardUploadFileResponse.buildError("uniqueIdentifier is empty");
            return shardUploadFileResponse;
        }

        //必须求出缓存中的PartETags，在分片合成文件中需要以此为依据，合并文件返回最终地址
        UploadContext.Content content = UploadContext.getUpload(shardUploadFileData.getUploadId());
        if (ObjectUtil.isNull(content)) {
            shardUploadFileResponse.buildError("file expiration...");
            return shardUploadFileResponse;
        }
        return shardUploadFileResponse;
    }
}
