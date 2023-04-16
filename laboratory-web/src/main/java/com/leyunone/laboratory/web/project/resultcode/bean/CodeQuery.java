package com.leyunone.laboratory.web.project.resultcode.bean;

import lombok.Data;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-17
 */
@Data
public class CodeQuery {
    
    private String code;
    
    private Integer tenantId;
    
    private Integer index;
    
    private Integer size;
}
