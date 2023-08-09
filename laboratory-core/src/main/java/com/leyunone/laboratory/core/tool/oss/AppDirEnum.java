package com.leyunone.laboratory.core.tool.oss;

/**
 * :)
 * appId - 文件夹
 *
 * @Author leyunone
 * @Date 2023/8/9 14:41
 */
public enum AppDirEnum {

    DEFAULT(1,"/default/"),
    
    ;

    AppDirEnum(Integer appId, String dir) {
        this.appId = appId;
        this.dir = dir;
    }

    private Integer appId;
    
    private String dir;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
    
    public static String getDir(Integer appId){
        for(AppDirEnum appDirEnum:AppDirEnum.values()){
            if(appDirEnum.getAppId().equals(appId)){
                return appDirEnum.getDir();
            }
        }
        return "/iothub/";
    }
}
