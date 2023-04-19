package com.leyunone.laboratory.web.project.resultcode.dao;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.web.project.resultcode.bean.Code;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeQuery;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
public interface CodeDao extends BaseDao<Code> {

    Page<Code> selectList(CodeQuery query);
    
    List<Code> selectNoTenant();
    
    Code selectLast(Integer tenantId);
}
