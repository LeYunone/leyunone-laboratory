package com.leyunone.laboratory.web.project.resultcode;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.core.regular.CodeRegular;
import com.leyunone.laboratory.core.util.AssertUtil;
import com.leyunone.laboratory.web.project.resultcode.bean.*;
import com.leyunone.laboratory.web.project.resultcode.dao.CodeDao;
import com.leyunone.laboratory.web.project.resultcode.dao.TenantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        String code = codeDTO.getCode();
        Integer tenantId = codeDTO.getTenantId();
        Code code1 = codeDao.selectOne(Code.builder().code(code).tenantId(tenantId).build());
        if(ObjectUtil.isNotNull(code1)){
            boolean is =false;
            if(ObjectUtil.isNotNull(codeDTO.getCodeId())){
                //更新check
                if(!codeDTO.getCodeId().equals(code1.getCodeId())){
                    is = true;
                }
            }else{
                is = true;
            }
            AssertUtil.isFalse(is,"状态码已存在");
        }
        codeDao.insertOrUpdate(codeDTO);
    }

    public Code getByCode(CodeQuery codeQuery) {
        return codeDao.selectOne(codeQuery);
    }

    public boolean delete(Integer code) {
        return codeDao.deleteById(code);
    }

    public CodeVO importCode(CodeDTO codeDTO)  {
        MultipartFile file = codeDTO.getFile();
        AssertUtil.isFalse(ObjectUtil.isNull(file), "文件不存在");
        File copyFile = null;
        List<Code> codes = new ArrayList<>();
        List<Code> errorList = new ArrayList<>();
        try {
            copyFile = MuFileUtil.multipartFileToFile("/temp/", file);
            Set<String> resultCode = CodeRegular.getResultCode(copyFile);
            List<Code> codes1;
            if(ObjectUtil.isNull(codeDTO.getTenantId())){
                codes1 = codeDao.selectNoTenant();
            }else{
                codes1 = codeDao.selectByCon(Code.builder().tenantId(codeDTO.getTenantId()).build());
            }
            Set<String> mycode = new HashSet<>();
            if (CollectionUtil.isNotEmpty(codes1)) {
                mycode = codes1.stream().map(Code::getCode).collect(Collectors.toSet());
            }
            if (CollectionUtil.isNotEmpty(resultCode)) {
                for (String prototype : resultCode) {
                    Code code = new Code();
                    code.setTenantId(codeDTO.getTenantId());
                    code.setPrototype(prototype);
                    //去掉左右括号
                    String newStr=prototype.replace("(","").replace(")","");
                    int index = newStr.indexOf(",");
                    code.setCode(newStr.substring(0, index));
                    code.setMessage(newStr.substring(index+1));
                    
                    if(codeDTO.getModel()==1 && mycode.contains(code.getCode())){
                        errorList.add(code);
                    }else{
                        codes.add(code);
                    }
                }
            }
            codeDao.insertOrUpdateBatch(codes);
        } finally {
            copyFile.delete();
        }
        return CodeVO.builder().errorList(errorList).successList(codes).build();
    }
    
    public Page<Code> getList(CodeQuery codeQuery){
        return codeDao.selectList(codeQuery);
    }
}
