package com.leyunone.laboratory.web.project.resultcode.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.core.dao.BaseRepository;
import com.leyunone.laboratory.web.project.resultcode.bean.Code;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeQuery;
import com.leyunone.laboratory.web.project.resultcode.dao.mapper.CodeMapper;
import org.springframework.stereotype.Repository;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@Repository
public class CodeRepository extends BaseRepository<CodeMapper, Code> implements CodeDao {
    
    @Override
    public Page<Code> selectList(CodeQuery query) {
        Page page = new Page(query.getIndex(),query.getSize());
        return this.baseMapper.selectCon(query,page);
    }
}
