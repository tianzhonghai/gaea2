package com.tim.gaea2.core.security;

import com.tim.gaea2.domain.service.UserInfoService;
import com.tim.gaea2.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by tianzhonghai on 2017/5/4.
 */
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User userVO = userInfoService.getUserVoByUserName(s);
        if(userVO == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        LoginUser loginUser = new LoginUser(userVO);
        return loginUser;
    }
}
