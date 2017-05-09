package com.tim.gaea2.core.security;

import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.model.SysUser;
import com.tim.gaea2.domain.service.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/5/4.
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        SysUser sysUser = userInfoService.getUserVoByUserName(s);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        LoginUser loginUser = new LoginUser(sysUser);
        return loginUser;
    }
}
