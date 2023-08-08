package com.leyunone.laboratory.core.tool.oss;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public enum  OssTypeEnum {
    
    IMAGES("images","图片桶"),
    
    FILE("files","文件桶")
    ;

    OssTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String type;

    private String desc;
}
