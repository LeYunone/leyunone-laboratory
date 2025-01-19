package com.leyunone.laboratory.core.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author leyunone
 * @date 2022-04-16
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Person {

    private String id;

    private String name;

    private int age;

    private List<Son> sons;

    private Long like;

    private LocalDateTime birthDay = LocalDateTime.now();

    public Person(int age) {
        this.age = age;
    }

    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
