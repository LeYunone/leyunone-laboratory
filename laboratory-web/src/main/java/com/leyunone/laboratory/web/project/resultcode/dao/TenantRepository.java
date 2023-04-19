package com.leyunone.laboratory.web.project.resultcode.dao;

import com.leyunone.laboratory.web.project.resultcode.bean.Tenant;
import com.leyunone.laboratory.web.project.resultcode.dao.mapper.TenantMapper;
import org.springframework.stereotype.Repository;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@Repository
public class TenantRepository extends BaseRepository<TenantMapper, Tenant> implements TenantDao {
    
}
