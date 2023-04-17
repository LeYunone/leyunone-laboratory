package com.leyunone.laboratory.web.project.resultcode.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeVO {

    private List<Code> successList;
    
    private List<Code> errorList;
}
