package com.leyunone.laboratory.core.system;

import lombok.SneakyThrows;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.PrintStream;

public class ReadClipboard implements ClipboardOwner{

    // 获取系统剪切板
    private Clipboard clipboard ;

    public static void startCtrlCToV(){
        //开启单独线程剪贴系统剪贴板
        new Thread(){
            @Override
            public void run(){
                new ReadClipboard();
                while(true){}
            }
        }.start();;
    }

    public ReadClipboard(){
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(clipboard.getContents(null), this);
    }

    public static void main(String[] args) {
        ReadClipboard.startCtrlCToV();
    }

    @SneakyThrows
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        //等待系统释放剪贴板资源
        Thread.sleep(10);
        String text = null;
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
            text = String.valueOf(clipboard.getData(DataFlavor.stringFlavor));
        }



        PrintStream stream=null;
        stream=new PrintStream("E:\\TheCore\\leyuna-laboratory\\laboratory-core/test.txt");//写入的文件path
        stream.print(text);//写入的字符串

        clipboard.setContents(clipboard.getContents(null), this);
    }
}
