package com.leyunone.laboratory.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * :)
 *
 * @Author pengli
 * @Date 2025/1/17 11:29
 */
@Configuration
public class WebJsonConfig4 {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supported = new ArrayList<>();
        supported.add(MediaType.APPLICATION_JSON);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supported);

        // JsonMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // null值的处理，必须要放在第一步，否则会将下面的日期格式化覆盖掉  null ==> ""
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString("");
            }
        });
        // 处理默认日期格式化：yyyy-MM-dd HH:mm:ss
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return mappingJackson2HttpMessageConverter;
    }
}
