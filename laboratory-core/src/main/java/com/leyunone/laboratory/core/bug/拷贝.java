package com.leyunone.laboratory.core.bug;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-10-30
 */
public class 拷贝 {

    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(1);

        Person clone = ObjectUtil.clone(person);
        clone.setAge(2);
        System.out.println(person);

        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
//        List<Person> cloneList = new ArrayList<>(personList);
//        cloneList.get(0).setAge(2);

        List<Person> clone1 = ObjectUtil.clone(personList);
//        List<Person> clone1 = CglibUtil.copyList(personList,Person::new);
        clone1.get(0).setAge(2);

        System.out.println(personList.get(0).getAge());
    }
}

@Data
class Person implements Serializable {
    private Integer age;
}
