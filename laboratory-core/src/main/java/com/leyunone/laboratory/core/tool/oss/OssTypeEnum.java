package com.leyunone.laboratory.core.tool.oss;

/**
 * :)
 *
 * @author leyunone
 * @email 365627310@qq.com
 * @date 2023-08-08
 */
public enum  OssTypeEnum {
    
    IMAGES("image","图片桶"),
    
    FILE("file","文件桶"),
    
    OTA("ota","ota文件桶"),

    PRODUCT_THEME("product-theme", "产品主题")
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
