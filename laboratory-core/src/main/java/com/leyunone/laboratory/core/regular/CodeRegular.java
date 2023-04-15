package com.leyunone.laboratory.core.regular;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 枚举中code码正则匹配
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-15
 */
public class CodeRegular {

    public static void main(String[] args) {
        String test ="(14124124,\"这是一个测试码1234\"),  (123123123,\"第二个测试码32124\")";
        String match = "[(].*[,\"].*[\")]";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(test);
    }
}
