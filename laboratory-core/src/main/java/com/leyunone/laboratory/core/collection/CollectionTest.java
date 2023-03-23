package com.leyunone.laboratory.core.collection;

import com.leyunone.laboratory.core.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @date 2022-04-17
 */
public class CollectionTest {

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        Person person = new Person();
        person.setAge(1);
        person.setId("1");
        Person person2 = new Person();
        person2.setAge(1);
        person2.setId("2");
        Person person3 = new Person();
        person3.setAge(3);
        person3.setId("3");
        list.add(person);
        list.add(person2);
        list.add(person3);
        UniqueSet uniqueSet = new UniqueSet<>(Person::getAge);
        uniqueSet.addAll(list);
        ArrayList<Person> arrayList = new ArrayList<>(uniqueSet);
        for(Person person1 : arrayList){
            System.out.println(person1);
        }
    }
}
