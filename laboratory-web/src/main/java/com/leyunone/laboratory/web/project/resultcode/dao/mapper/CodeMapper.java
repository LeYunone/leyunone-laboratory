package com.leyunone.laboratory.web.project.resultcode.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.web.project.resultcode.bean.Code;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-17
 */
public interface CodeMapper extends BaseMapper<Code> {
    
    Page<Code> selectCon(@Param("con") CodeQuery query, Page page);
}
