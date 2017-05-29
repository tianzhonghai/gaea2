package com.tim.gaea2.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tianzhonghai on 2017/5/4.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @RequestMapping("/index")
    public String index(){
        return "home/index";
    }

    @RequestMapping("/calc")
    public String calc(){
        int a = 0,b = 0,c = 0;
        a = b / c;
        return "home/test";
    }
}
