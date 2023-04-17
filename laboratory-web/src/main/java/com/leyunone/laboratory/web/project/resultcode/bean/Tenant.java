package com.leyunone.laboratory.web.project.resultcode.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-17
 */
@Data
@TableName("t_tenant")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tenant {
    
    @TableId(type = IdType.AUTO)
    private Integer tenantId;
    
    private String tenantName;
}
