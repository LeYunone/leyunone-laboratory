package com.leyunone.laboratory.core.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 沙雕代码 猴子排序
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-01
 */
public class MonkeySort {

    public static void main(String[] args) {
        monkeySort();
    }
    
    public static void monkeySort(){
        Integer [] arr = new Integer []{6,4,1,7,8,9};
        List<Integer> result = null;
        int d = 0;
        back: while(true){
            System.out.println(d+++"次");
            List<Integer> arrrs= Arrays.asList(arr);
            Collections.shuffle(arrrs);
            for(int i=0;i<arrrs.size()-1;i++){
                if(arrrs.get(i + 1) < arrrs.get(i)){
                    continue back;
                }
            }
            result = arrrs;
            break;
        }
        for(Integer i :result){
            System.out.println(i);
        }
    }
}
