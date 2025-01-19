package com.leyunone.laboratory.core.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class FlinkDemo {
    public static void main(String[] args) throws Exception {
        // 设置执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从元素创建数据流
        DataStream<String> dataStream = env.fromElements("Apache Flink is a powerful stream processing framework",
                "It supports various data processing operations",
                "This is a simple demo for Flink");

        // 1. 简单的 Map 操作
        SingleOutputStreamOperator<String> mappedStream = dataStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                return value.toUpperCase();
            }
        });

        // 2. 扁平映射操作
        SingleOutputStreamOperator<String> flatMappedStream = dataStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                for (String word : value.split(" ")) {
                    out.collect(word);
                }
            }
        });

        // 3. 聚合操作
        SingleOutputStreamOperator<Tuple2<String, Integer>> wordCountStream = flatMappedStream
              .map(new MapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> map(String value) throws Exception {
                        return new Tuple2<>(value, 1);
                    }
                })
              .keyBy(0)
              .sum(1);

        // 4. 窗口操作
        SingleOutputStreamOperator<Tuple2<String, Integer>> windowedStream = flatMappedStream
              .map(new MapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> map(String value) throws Exception {
                        return new Tuple2<>(value, 1);
                    }
                })
              .keyBy(0)
              .countWindow(3)
              .sum(1);

        // 输出结果
        mappedStream.print("Mapped Stream");
        flatMappedStream.print("Flat Mapped Stream");
        wordCountStream.print("Word Count Stream");
        windowedStream.print("Windowed Stream");

        // 执行程序
        env.execute("Flink Demo");
    }
}