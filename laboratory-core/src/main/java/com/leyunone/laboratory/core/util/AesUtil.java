package com.leyunone.laboratory.core.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.io.IOException;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/12/14 15:27
 */
public class AesUtil {

    public static void main(String[] args) throws IOException, InterruptedException {
        String content = "test中文";
        //随机生成密钥
        String key = "mykeyderqgfazcxg";
        String jia = getJia(key, content);
        System.out.println(jia);
        System.out.println(getJie(key,jia));
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
        //加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        System.out.println(encryptHex);

        //解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }

    public static String getJia(String key,String content){
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
        return aes.encryptHex(content);
    }

    public static String getJie(String key,String content){
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
        return aes.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
    }

}
