package com.leyunone.laboratory.core.regular;


import cn.hutool.core.io.file.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Source;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 枚举中code码正则匹配
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-15
 */
public class CodeRegular {

    private final static Logger logger  = LoggerFactory.getLogger(CodeRegular.class);
    
    public static void main(String[] args) {
        CodeRegular.getResultCode(new File("E:\\TheCore\\leyuna-laboratory\\laboratory-core\\src\\main\\java\\com\\leyunone\\laboratory\\core\\bean\\ResultCode.java"));
    }
    
    public static Set<String> getResultCode(File file){
        FileReader fileReader = new FileReader(file);
        String result = fileReader.readString();

        String match = "[(]\\d*[,\"].*[\")]";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(result);
        Set<String> rSet = new HashSet<>();
        while(matcher.find()){
            rSet.add(matcher.group());
        }
        logger.info("=========count:{}",rSet.size());
        return rSet;
    }
}
