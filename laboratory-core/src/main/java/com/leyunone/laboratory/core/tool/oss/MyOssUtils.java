package com.leyunone.laboratory.core.tool.oss;

import com.leyunone.laboratory.core.tool.oss.service.FileResolve;
import com.leyunone.laboratory.core.tool.oss.service.OssService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/8/9 10:07
 */
public class MyOssUtils {

    private MyOssUtils() {
    }

    private MyOssUtils(Integer appId, OssTypeEnum ossType) {
        this.appId = appId;
        this.ossType = ossType;
    }

    private Integer appId;

    private OssTypeEnum ossType;

    public static MyOssUtils build(OssTypeEnum ossType, Integer appId) {
        return new MyOssUtils(appId,ossType);
    }

    public static MyOssUtils build(OssTypeEnum ossType) {
        return build(ossType, null);
    }

    public String uploadFile(String name, InputStream stream) {
        ExtensionLoader<FileResolve> extensionLoader = ExtensionLoader.getExtensionLoader(FileResolve.class);
        FileResolve my = extensionLoader.getExtension("my");
        Map<String, String> map = new HashMap<>();
        map.put("ossPattern", ossType.getType());
        URL url = new URL("", "", 0, map);
        return my.uploadFile(url, appId, name, stream);
    }

    public String getFileUrl(String name, Long expireTime) {
        ExtensionLoader<OssService> extensionLoader = ExtensionLoader.getExtensionLoader(OssService.class);
        OssService ossService = extensionLoader.getExtension(ossType.getType());   
        return ossService.getFileUrl(name,expireTime);
    }

    public String getFileUrl(String name) {
        return this.getFileUrl(name,null);
    }

    public void downFile(OutputStream outputStream, String objectName) {
        ExtensionLoader<OssService> extensionLoader = ExtensionLoader.getExtensionLoader(OssService.class);
        OssService ossService = extensionLoader.getExtension(ossType.getType());
        ossService.downFile(outputStream,objectName);
    }

    public void deleteFile(String name) {
        ExtensionLoader<OssService> extensionLoader = ExtensionLoader.getExtensionLoader(OssService.class);
        OssService ossService = extensionLoader.getExtension(ossType.getType());
        ossService.deleteFile(name);
    }

}
