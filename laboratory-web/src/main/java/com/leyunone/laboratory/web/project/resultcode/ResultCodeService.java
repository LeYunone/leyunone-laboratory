package com.leyunone.laboratory.web.project.resultcode;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.laboratory.core.regular.CodeRegular;
import com.leyunone.laboratory.core.util.AssertUtil;
import com.leyunone.laboratory.web.project.resultcode.bean.Code;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeDTO;
import com.leyunone.laboratory.web.project.resultcode.bean.CodeQuery;
import com.leyunone.laboratory.web.project.resultcode.dao.CodeDao;
import com.leyunone.laboratory.web.project.resultcode.dao.TenantDao;
import com.sun.tools.javac.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        codeDao.insertOrUpdate(codeDTO);
    }

    public Code getByCode(CodeQuery codeQuery) {
        return codeDao.selectOne(codeQuery);
    }

    public boolean delete(Integer code) {
        return codeDao.deleteById(code);
    }

    public void importCode(CodeDTO codeDTO)  {
        MultipartFile file = codeDTO.getFile();
        AssertUtil.isFalse(ObjectUtil.isNull(file), "文件不存在");
        File copyFile = null;
        try {
            copyFile = MuFileUtil.multipartFileToFile("/temp/" + file.getOriginalFilename(), file);
            Set<String> resultCode = CodeRegular.getResultCode(copyFile);
            List<Code> codes = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(resultCode)) {
                for (String prototype : resultCode) {
                    Code code = new Code();
                    code.setTenantId(codeDTO.getTenantId());
                    code.setPrototype(prototype);
                    //去掉左右括号
                    String newStr=prototype.replace("(","").replace(")","");
                    int index = newStr.indexOf(",");
                    code.setCode(newStr.substring(0,index));
                    code.setMessage(newStr.substring(index+1));
                    codes.add(code);
                }
            }
            codeDao.insertOrUpdateBatch(codes);
        } finally {
            copyFile.delete();
        }


    }
    
    public Page<Code> getList(CodeQuery codeQuery){
        return codeDao.selectList(codeQuery);
    }
}
