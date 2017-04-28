package com.tim.gaea2.web.controllers;

import com.tim.gaea2.core.utils.SecretUtils;
import com.tim.gaea2.domain.service.UserInfoService;
import com.tim.gaea2.domain.service.UserVO;
import com.tim.gaea2.web.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/3/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/index")
    public String index(Model model){
        UserVO userVO = userInfoService.getUserVoByUserId(1);
        model.addAttribute("User",userVO);
//        int a = 0,b = 0,c = 0;
//        a = b / c;
        return "users/index";
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    @ResponseBody
    public List<UserVO> getUserList(){
        List<UserVO> userVOs = userInfoService.getAllUserVOs();

        return userVOs;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(UserModel userModel, Model model){
        String pwd = "111111";
        try{
            pwd = SecretUtils.MD5(pwd);
        }catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }

        UserVO userVO = new UserVO();
        userVO.setUserName(userModel.getUserName());
        userVO.setPassword(pwd);
        userInfoService.addUserVO(userVO);

        userVO = userInfoService.getUserVoByUserId(1);
        model.addAttribute("User",userVO);
        return "redirect:/user/index";
    }
}
