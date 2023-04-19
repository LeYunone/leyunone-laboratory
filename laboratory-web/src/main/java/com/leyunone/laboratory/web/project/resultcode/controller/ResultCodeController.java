package com.leyunone.laboratory.web.project.resultcode.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.web.project.resultcode.bean.*;
import com.leyunone.laboratory.web.project.resultcode.dao.TenantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@RestController
@RequestMapping("/resultcode")
public class ResultCodeController {

    @Autowired
    private ResultCodeService resultCodeService;
    @Autowired
    private LuceneService luceneService;

    @GetMapping("/list")
    public DataResponse listQuery(CodeQuery query) {
        Page<Code> list = resultCodeService.getList(query);
        return DataResponse.of(list);
    }

    /**
     * 搜索引擎模式 Luence
     *
     * @return
     */
    @GetMapping("/search")
    public DataResponse searchQuery(SearchQuery query) {
        return luceneService.getCodeDir(query.getCode(), query.getIndex(), query.getSize());
    }

    @GetMapping("/createDir")
    public DataResponse createDir() {
        luceneService.clearDir();
        luceneService.createCodeDir(null);
        return DataResponse.of();
    }

    /**
     * 保存
     *
     * @return
     */
    @PostMapping("/save")
    public DataResponse save(@RequestBody CodeDTO dto) {
        resultCodeService.save(dto);
        return DataResponse.of();
    }

    /**
     * 根据主键查找
     *
     * @return
     */
    @GetMapping("/byid")
    public DataResponse getById(CodeQuery query) {
        Code byCode = resultCodeService.getByCode(query);
        AssertUtil.isFalse(ObjectUtil.isNull(byCode), "索引失效");
        return DataResponse.of(byCode);
    }

    @GetMapping("/delete")
    public DataResponse delete(Integer code) {
        resultCodeService.delete(code);
        return DataResponse.of();
    }

    /**
     * 导入文件 解析状态码
     *
     * @return
     */
    @PostMapping("/import")
    public DataResponse importCode(CodeDTO dto) {
        CodeVO codeVO = resultCodeService.importCode(dto);
        return DataResponse.of(codeVO);
    }

    @Autowired
    private TenantDao tenantDao;

    @GetMapping("/tenant")
    public DataResponse tenants() {
        List<Tenant> tenants = tenantDao.selectByCon(null);
        return DataResponse.of(tenants);
    }

    @GetMapping("/tenantname")
    public DataResponse tenantByName(String name) {
        Tenant tenant = tenantDao.selectOne(Tenant.builder().tenantName(name).build());
        return DataResponse.of(tenant);
    }

    @GetMapping("/tenantdelete")
    public DataResponse tenantDelete(Integer id) {
        tenantDao.deleteById(id);
        return DataResponse.of();
    }


    @PostMapping("tenantsave")
    public DataResponse tenantSave(@RequestBody Tenant tenant) {
        Tenant tenant1 = tenantDao.selectOne(Tenant.builder().tenantName(tenant.getTenantName()).build());
        if (ObjectUtil.isNotNull(tenant1)) {
            boolean is = false;
            if (ObjectUtil.isNotNull(tenant.getTenantId())) {
                //更新check
                if (!tenant1.getTenantId().equals(tenant.getTenantId())) {
                    is = true;
                }
            } else {
                is = true;
            }
            AssertUtil.isFalse(is, "名字重复");
        }

        tenantDao.insertOrUpdate(tenant);
        return DataResponse.of();
    }
}
