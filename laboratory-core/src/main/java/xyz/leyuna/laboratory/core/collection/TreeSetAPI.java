package xyz.leyuna.laboratory.core.collection;

import cn.hutool.core.collection.ListUtil;
import org.springframework.stereotype.Service;
import xyz.leyuna.laboratory.core.bean.Person;

import java.util.*;

/**
 * @author pengli
 * @date 2022-04-16
 * Tree Set 自定义比较器妙用
 */
@Service
public class TreeSetAPI<R> {

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        Person person = new Person();
        Person person2 = new Person();
        Person person3 = new Person();
        person.setAge(1);
        person.setId("1");
        person.setName("1");
        person2.setAge(1);
        person2.setId("2");
        person2.setName("2");
        person3.setAge(3);
        person3.setId("3");
        person3.setName("3");
        list.add(person3);
        list.add(person);
        list.add(person2);
        
        TreeSetAPI treeSetAPI = new TreeSetAPI();
//        treeSetAPI.distinctExe(list,);
        ListUtil.sortByProperty(list,"id");
        for(Person person1:list){
            System.out.println(person1);
        }
    }
    
    /**
     * 去重
     */
    public ArrayList distinctExe(List<R> list, String column){
        
        Set be_set = new TreeSet(new Comparator<R>() {

            @Override
            public int compare(R o1, R o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        be_set.addAll(list);
        return new ArrayList<>(be_set);
    }
}
