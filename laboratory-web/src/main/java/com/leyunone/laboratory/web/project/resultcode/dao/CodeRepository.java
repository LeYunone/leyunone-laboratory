package com.leyunone.laboratory.web.project.resultcode.dao;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.web.project.resultcode.bean.Code;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeQuery;
import com.leyunone.laboratory.web.project.resultcode.dao.mapper.CodeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@Repository
public class CodeRepository extends BaseRepository<CodeMapper, Code> implements CodeDao {

    @Override
    public Page<Code> selectList(CodeQuery query) {
        Page page = new Page(query.getIndex(), query.getSize());
        return this.baseMapper.selectCon(query, page);
    }

    @Override
    public List<Code> selectNoTenant() {
        LambdaQueryWrapper<Code> lambda = new QueryWrapper<Code>().lambda();
        lambda.isNull(Code::getTenantId);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public Code selectLast(Integer tenantId) {
        LambdaQueryWrapper<Code> lambda = new QueryWrapper<Code>().lambda();
        lambda.eq(ObjectUtil.isNotNull(tenantId), Code::getTenantId, tenantId);
        lambda.isNull(ObjectUtil.isNull(tenantId), Code::getTenantId);
        lambda.orderByDesc(Code::getCode);
        List<Code> codes = this.baseMapper.selectList(lambda);
        return CollectionUtil.isNotEmpty(codes) ? codes.get(0) : null;
    }
}
