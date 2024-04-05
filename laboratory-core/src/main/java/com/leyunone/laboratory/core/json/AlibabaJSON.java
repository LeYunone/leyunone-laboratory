package com.leyunone.laboratory.core.json;


import com.alibaba.fastjson.JSONObject;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/20
 */
public class AlibabaJSON {

    public static void main(String[] args) {
        JSONObject.parseObject("     { \n" +
                "         \"capabilityResources\": {\n" +
                "                \"friendlyNames\": [\n" +
                "                  {\n" +
                "                    \"@type\": \"d\",\n" +
                "                    \"value\": {\n" +
                "                      \"assetId\": \"Alexa.Setting.Opening\"\n" +
                "                    }\n" +
                "                  }\n" +
                "                ]\n" +
                "              }\n" +
                "     }");
    }
}
