package com.leyunone.laboratory.core.util;

import java.io.*;

public class CopyFile {

    public static void main(String[] args) {
        try {
            copyDir("E:\\copy","F:\\copy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void copyDir(String sourceDir,String targetDir) throws Exception{
        File source=new File(sourceDir);
        //判断源文件目录是存在
        if (!source.exists()||!source.isDirectory()){
            return;
        }
        File target=new File(targetDir);
        //判断目标文件目录是存在
        if(!target.exists()||!target.isDirectory()){
            target.mkdirs();
        }
        //遍历源文件
        File [] files=source.listFiles();
        for (int i=0;i<files.length;i++){
            if (files[i].isFile()){
                //源文件
                File sourceFile=files[i];
                //目标文件
                File targetFile=new File(targetDir,files[i].getName());

                BufferedInputStream buffIn=null;
                BufferedOutputStream buffOut=null;
                try {
                    //文件字节输入流
                    buffIn=new BufferedInputStream(new FileInputStream(sourceFile));
                    //文件字节输出流
                    buffOut=new BufferedOutputStream(new FileOutputStream(targetFile));
                    byte[] b=new byte[1024*5];
                    int len;
                    while ((len=buffIn.read(b))!=-1){
                        buffOut.write(b, 0, len);
                    }
                    buffOut.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    if (buffIn!=null){
                        buffIn.close();
                    }if (buffOut!=null){
                        buffOut.close();
                    }
                }
            }
            //如果是文件夹，递归复制文件
            if (files[i].isDirectory()){
                String subSourceDir= sourceDir+File.separator+files[i].getName();
                String subTargetDir= targetDir+File.separator+files[i].getName();
                copyDir(subSourceDir, subTargetDir);
            }
        }
    }

}

