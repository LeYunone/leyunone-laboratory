//package com.leyunone.laboratory.core.json;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * :)
// *
// * @Author pengli
// * @Date 2025/1/16 13:53
// */
//@Configuration
//public class WebJsonConfig3 {
//
//    @Bean
//    public HttpMessageConverters jsonConverters() {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.IgnoreNonFieldGetter,
//                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        List<MediaType> supportedMediaTypes = new ArrayList<>();
////        supportedMediaTypes.add(new MediaType("text", "json", Charset.forName("utf8")));
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
//        HttpMessageConverter<?> converter = fastConverter;
//        return new HttpMessageConverters(converter);
//    }
//}
