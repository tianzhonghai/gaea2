package com.tim.gaea2.core.security;

import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.entity.UserRolePOExample;
import com.tim.gaea2.domain.model.User;
import com.tim.gaea2.domain.repository.UserRolePOMapper;
import com.tim.gaea2.domain.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 登录用户
 * Created by Tim on 2017/5/7.
 */
public class LoginUser implements UserDetails {

    private User user;

    public LoginUser(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();

        List<UserRolePO> list = user.getAllUserRolePOs();
        for (UserRolePO po : list){
            auths.add(new SimpleGrantedAuthority(po.getRoleId().toString()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
