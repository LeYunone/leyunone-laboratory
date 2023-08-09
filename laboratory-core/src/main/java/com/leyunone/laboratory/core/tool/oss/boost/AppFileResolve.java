package com.leyunone.laboratory.core.tool.oss.boost;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.laboratory.core.tool.oss.AppDirEnum;
import com.leyunone.laboratory.core.tool.oss.service.FileResolve;
import com.leyunone.laboratory.core.tool.oss.service.OssService;
import org.apache.dubbo.common.URL;

import java.io.InputStream;

/**
 * :)
 * 文件操作前的前置处理
 *
 * @Author leyunone
 * @Date 2023/8/9 10:53
 */
public class AppFileResolve implements FileResolve {

    private OssService ossService;

    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }

    /**
     * 上传文件前的文件处理
     *
     * @param url
     * @param appId
     * @param name
     * @param stream
     * @return
     */
    @Override
    public String uploadFile(URL url, Integer appId, String name, InputStream stream) {
        //根据appId自动定位应用文件夹
        if (ObjectUtil.isNotNull(appId)) {
            name = AppDirEnum.getDir(appId) + name;
        }
        return ossService.uploadFile(url,name, stream);
    }
}
