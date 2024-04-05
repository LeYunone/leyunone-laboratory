package com.leyunone.laboratory.core.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;

import java.io.Serializable;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/22
 */
public class SerializableIdTest implements StpInterface {

    public static void main(String[] args) {
        StpUtil.login(10001);
        Object i = 1;
        idDoing(1);
        String a = "1";
        idDoing(1);
    }

    public static void idDoing(Serializable id) {

    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
