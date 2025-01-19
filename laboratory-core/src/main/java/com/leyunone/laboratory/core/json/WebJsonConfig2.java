//package com.leyunone.laboratory.core.json;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Configuration
//public class WebJsonConfig2 implements WebMvcConfigurer {
//    
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //json序列化的转化
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(
//                //json格式输出
//                SerializerFeature.PrettyFormat,
//                // 保留map为空的字段
//                SerializerFeature.WriteMapNullValue,
//                // 将String类型的null转成""形式
//                SerializerFeature.WriteNullStringAsEmpty,
//                // 将Number类型的null转成0
//                SerializerFeature.WriteNullNumberAsZero,
//                // 将List类型的null转成[],而不是""
//                SerializerFeature.WriteNullListAsEmpty,
//                // Boolean类型的null转成false
//                SerializerFeature.WriteNullBooleanAsFalse,
//                // 处理可能循环引用的问题
//                SerializerFeature.DisableCircularReferenceDetect);
//        converter.setFastJsonConfig(config);
//        converter.setDefaultCharset(StandardCharsets.UTF_8);
//        List<MediaType> mediaTypeList = new ArrayList<>();
//        mediaTypeList.add(MediaType.APPLICATION_JSON);
//        converter.setSupportedMediaTypes(mediaTypeList);
//        converters.add(converter);
//        
//        //值映射关系的转化
//        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        /**
//         * 序列换成Json时,将所有的Long变成String
//         * 因为js中得数字类型不能包括所有的java Long值
//         */
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//
//        // 所有的double类型返回保留三位小数
//        objectMapper.registerModule(simpleModule);
//        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        converters.add(jackson2HttpMessageConverter);
//    }
//}