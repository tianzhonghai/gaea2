package com.tim.gaea2.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tianzhonghai on 2017/5/4.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(){
        return "home/index";
    }
}
