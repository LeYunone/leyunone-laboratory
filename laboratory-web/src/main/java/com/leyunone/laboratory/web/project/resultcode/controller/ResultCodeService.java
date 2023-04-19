package com.leyunone.laboratory.web.project.resultcode.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.web.project.resultcode.bean.Code;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeDTO;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeQuery;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeVO;
import com.leyunone.laboratory.web.project.resultcode.dao.CodeDao;
import com.leyunone.laboratory.web.project.resultcode.dao.TenantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@Service
public class ResultCodeService {

    @Autowired
    private CodeDao codeDao;
    @Autowired
    private TenantDao tenantDao;

    public void save(CodeDTO codeDTO) {
        Integer tenantId = codeDTO.getTenantId();
        if (ObjectUtil.isNull(codeDTO.getCodeId())) {
            //新增 如果没有错误码 则自增
            if (ObjectUtil.isNull(codeDTO.getCode())) {
                Code code2 = codeDao.selectLast(codeDTO.getTenantId());
                AssertUtil.isFalse(ObjectUtil.isNull(code2),"该租户中，错误码必填");
                codeDTO.setCode(code2.getCode()+1);
            }
        }
        Code code1 = codeDao.selectOne(Code.builder().code(codeDTO.getCode()).tenantId(tenantId).build());
        if (ObjectUtil.isNotNull(code1)) {
            boolean is = false;
            if (ObjectUtil.isNotNull(codeDTO.getCodeId())) {
                //更新check
                if (!codeDTO.getCodeId().equals(code1.getCodeId())) {
                    is = true;
                }
            } else {
                is = true;
            }
            AssertUtil.isFalse(is, "状态码已存在");
        }
        codeDao.insertOrUpdate(codeDTO);
    }

    public Code getByCode(CodeQuery codeQuery) {
        return codeDao.selectOne(codeQuery);
    }

    public boolean delete(Integer code) {
        return codeDao.deleteById(code);
    }

    public CodeVO importCode(CodeDTO codeDTO) {
        MultipartFile file = codeDTO.getFile();
        AssertUtil.isFalse(ObjectUtil.isNull(file), "文件不存在");
        File copyFile = null;
        List<Code> codes = new ArrayList<>();
        List<Code> errorList = new ArrayList<>();
        try {
            copyFile = MuFileUtil.multipartFileToFile("/opt/temp/", file);
            Set<String> resultCode = CodeRegular.getResultCode(copyFile);
            List<Code> codes1;
            if (ObjectUtil.isNull(codeDTO.getTenantId())) {
                codes1 = codeDao.selectNoTenant();
            } else {
                codes1 = codeDao.selectByCon(Code.builder().tenantId(codeDTO.getTenantId()).build());
            }
            Map<Long, Code> mycode = new HashMap<>();
            if (CollectionUtil.isNotEmpty(codes1)) {
                mycode = codes1.stream().collect(Collectors.toMap(Code::getCode, x -> x, (k1, k2) -> k1));
            }
            if (CollectionUtil.isNotEmpty(resultCode)) {
                for (String prototype : resultCode) {
                    Code code = new Code();
                    code.setTenantId(codeDTO.getTenantId());
                    code.setPrototype(prototype);
                    //去掉左右括号
                    String newStr = prototype.replace("(", "").replace(")", "");
                    int index = newStr.indexOf(",");
                    code.setCode(Long.parseLong(newStr.substring(0, index)));
                    code.setMessage(newStr.substring(index + 1));

                    if (codeDTO.getModel() == 1) {
                        if (mycode.containsKey(code.getCode())) {
                            errorList.add(code);
                        } else {
                            codes.add(code);
                        }
                    } else {
                        if (mycode.containsKey(code.getCode())) {
                            Code code1 = mycode.get(code.getCode());
                            code.setCodeId(code1.getCodeId());
                        }
                        codes.add(code);
                    }

                }
            }
            codeDao.insertOrUpdateBatch(codes);
        } finally {
            FileUtil.del(copyFile);
        }
        return CodeVO.builder().errorList(errorList).successList(codes).build();
    }

    public Page<Code> getList(CodeQuery codeQuery) {
        return codeDao.selectList(codeQuery);
    }
}
