package com.leyunone.laboratory.web.project.resultcode.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeDTO {
    
    private Integer codeId;

    private Long code;
    
    private Integer tenantId;
    
    private String message;
    
    private MultipartFile file;
    
    private Integer model;
}
