package com.leyunone.laboratory.core.file.core.auto;

import com.leyunone.laboratory.core.file.core.UploadFileController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/12/27 15:07
 */
@ComponentScan("com.leyunone.laboratory.core.file")
@Configuration
@ConditionalOnMissingBean(UploadFileController.class)
public class UploadFileFrameAutoConfig {

    @Bean
    public UploadFileController uploadFileController() {
        return new UploadFileController();
    }
}
