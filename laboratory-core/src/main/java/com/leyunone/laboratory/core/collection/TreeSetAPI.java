package com.leyunone.laboratory.core.collection;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * @author pengli
 * @date 2022-04-16
 * Tree Set 自定义比较器妙用
 */
@Service
public class TreeSetAPI<R> {

    public ArrayList distinctExe(List<R> list, String column){
        Function<Integer,Integer> f1  = i->i+2;

        Set be_set = new TreeSet(new Comparator<R>() {

            @Override
            public int compare(R o1, R o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        be_set.addAll(list);
        return new ArrayList<>(be_set);
    }

    public static <T,K>List<T> distinct(Collection<T> collection, Function<T,K> uniqueConditon){
        UniqueSet<K,T> uniqueSet = new UniqueSet<>(uniqueConditon);
        uniqueSet.addAll(collection);
        return new ArrayList<>(uniqueSet);
    }
}
