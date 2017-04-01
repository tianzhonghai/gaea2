package com.tim.gaea2.web.controllers;

import com.tim.gaea2.domain.service.UserInfoService;
import com.tim.gaea2.domain.service.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileOutputStream;

/**
 * Created by tianzhonghai on 2017/3/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/index")
    public String index(Model model){

        UserVO userVO = userInfoService.getUserVoByUserId(1);

        model.addAttribute("User",userVO);

        return "users/index";
    }



    @RequestMapping("/index2")
    public String index2(){

        return "index";

    }
}
