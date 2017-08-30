package com.tim.gaea2.web.controllers;

import com.sun.javafx.collections.MappingChange;
import com.tim.gaea2.core.shiro.ShiroUser;
import com.tim.gaea2.core.utils.HttpClientUtil;
import com.tim.gaea2.domain.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianzhonghai on 2017/5/4.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @RequestMapping("/index")
    public String index(Model model){
        Object obj = SecurityUtils.getSubject().getPrincipal();
        ShiroUser current = (ShiroUser)obj;
        model.addAttribute("User",current);
        return "home/index";
    }

    @RequestMapping("/main")
    public String main() {
        return "home/main";
    }
}
