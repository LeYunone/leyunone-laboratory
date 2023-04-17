package com.leyunone.laboratory.web.project.resultcode.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@Data
@TableName("t_code")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    
    @TableId(type = IdType.AUTO)
    private Integer codeId;

    private String code;

    private Integer tenantId;

    private String message;
    
    private String prototype;
    
    @TableField(exist = false)
    private String tenantName;
    
    @TableField(exist = false)
    private String codeHeight;
}
