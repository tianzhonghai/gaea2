package com.tim.gaea2.domain.model;

import com.tim.gaea2.core.utils.SpringUtil;
import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Tim on 2017/2/12.
 */
public class SysUser {
    private Long id;

    private String userName;

    private String password;

    private String state;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public List<UserRolePO> getAllUserRolePOs(){
        UserInfoService userInfoService = SpringUtil.getBean(UserInfoService.class);
        List<UserRolePO> list = userInfoService.getUserRolesByUserId(id);
        return list;
    }
}