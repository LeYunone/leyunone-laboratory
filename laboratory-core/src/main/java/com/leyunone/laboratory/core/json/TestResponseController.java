package com.leyunone.laboratory.core.json;

import com.leyunone.laboratory.core.bean.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * :)
 *
 * @Author pengli
 * @Date 2025/1/16 11:02
 */
@RestController
@RequestMapping("/json/response")
public class TestResponseController {

    @GetMapping("/noneJson")
    public Person noneResponse() {
        Person p = new Person();
        p.setAge(1);
        p.setLike(2L);
        return p;
    }
}
