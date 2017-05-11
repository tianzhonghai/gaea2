package com.tim.gaea2.web.controllers;

import com.google.common.cache.Cache;
import com.tim.gaea2.core.utils.GuavaCacheUtils;
import com.tim.gaea2.core.utils.SecretUtils;
import com.tim.gaea2.domain.model.SysUser;
import com.tim.gaea2.domain.service.UserInfoService;
import com.tim.gaea2.web.models.UserModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @RequestMapping("/index")
    @RequiresPermissions("user:index")
    public String index(){
//        int a = 0,b = 0,c = 0;
//        a = b / c;
        return "users/index";
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("user:index")
    public List<SysUser> getUserList(){
        List<SysUser> userVOs = userInfoService.getAllUserVOs();
        return userVOs;
    }

    @RequiresPermissions("user:view")
    @RequestMapping("/view")
    public String view(int id,Model model){
        SysUser userVO = userInfoService.getUserVoByUserId(id);
        model.addAttribute("User",userVO);
        return "users/view";
    }

    @RequiresPermissions("user:detail")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable int id, Model model) throws Exception{

        Cache<String,Object> cache = GuavaCacheUtils.getCache();

        SysUser userVO = (SysUser)cache.get(Integer.toString(id),()->{
            return userInfoService.getUserVoByUserId(id);
        });
        model.addAttribute("User",userVO);
        return "users/view";
    }

    @RequiresPermissions("user:add")
    @RequestMapping("/add")
    public String add(){
        return "users/add";
    }

    @RequiresPermissions("user:add")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(UserModel userModel, Model model){
        String pwd = "111111";
        try{
            pwd = SecretUtils.MD5(pwd);
        }catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }

        org.dozer.Mapper mapper = null;
        try {
            mapper = dozerBean.getObject();
        }catch (Exception ex){}

        SysUser userVO = mapper.map(userModel,SysUser.class);
        userVO.setPassword(pwd);
        userInfoService.addUserVO(userVO);

        return "redirect:/user/index";
    }
}
