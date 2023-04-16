package com.leyunone.laboratory.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-16
 */
@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/resultcode")
    public String htmlView(){
        return "resultcode";
    }
}
