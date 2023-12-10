package com.leyunone.laboratory.jdk9sample.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/12/11
 */
public class StreamTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        // 创建一个包含null的流
        Stream<String> stream = Stream.ofNullable(null);

        // 打印流中的元素
        stream.forEach(System.out::println); // 输出为null

        IntStream.range(1, 10).map(i -> i * 2).forEach(System.out::println);
    }
}
