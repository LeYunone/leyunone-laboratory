package com.leyunone.laboratory.core.system;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;

/**
 * @author LeYuna
 * @date 2022-08-03
 *  监听剪贴板 内容到txt中
 */
public class SystemClipboard implements ClipboardOwner {

    //复制时间 是
    private Long ctrlVTime;
    //输出模式 0展示框 1编辑框
    private Integer type;
    //路径 随意填
    private String path;


    /**
     * 把这个B启动
     * @param args
     */
    public static void main(String[] args) {
        String path = "D:";
        Long time = 1500L;
        Integer type = 1;
        SystemClipboard.startCtrlCToV(path,time,type);
    }



    public static void startCtrlCToV(String path,Long ctrlVTime,Integer type){
        SystemClipboard systemClipboard = null;
        try {
            systemClipboard = new SystemClipboard(path,ctrlVTime,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {

        }
    }

    private Clipboard clipboard;
    private String currentStr;

    public SystemClipboard(String path,Long ctrlVTime,Integer type) throws IOException {
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(clipboard.getContents(null), this);
        path = path+"/test.html";
        this.path = path;
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        //
        this.ctrlVTime = ctrlVTime;
        this.type = type;
        Desktop.getDesktop().open(new File(path));
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        try {
            //缓冲时间
            Thread.sleep(ctrlVTime);

            String text = null;
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                text = String.valueOf(clipboard.getData(DataFlavor.stringFlavor));
            }
            //休息两轮
            if (text.equals(this.currentStr)) {
                //自定义 从复制到粘贴出来所用的时间，超过这个时间的话 剪贴板将再一次被加密
                Thread.sleep(ctrlVTime);
                clipboard.setContents(clipboard.getContents(text), this);
                return;
            }

            StringBuilder stringHtml = new StringBuilder();

            //输入HTML文件内容
            stringHtml.append("<html><head>");
            stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
            stringHtml.append("<title>冲破他的限制！！！</title>");
            stringHtml.append("</head>");
            stringHtml.append("<body>");
            if (type == 0) {
                stringHtml.append("<pre>");
                stringHtml.append("<code>");
                stringHtml.append(text);
                stringHtml.append("</pre>");
                stringHtml.append("</code>");
            } else {
                stringHtml.append("<textarea style=\"width: 100%;height: 100%\">");
                stringHtml.append(text);
                stringHtml.append("</textarea>");
            }

            stringHtml.append("</body></html>");
            FileOutputStream o = new FileOutputStream(path);
            o.write(stringHtml.toString().getBytes("GBK"));
            o.close();
            this.currentStr = text;
            clipboard.setContents(clipboard.getContents(text), this);
        }catch (Exception e){

        }
    }
}
