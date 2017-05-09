package com.tim.gaea2.core.security;

import com.tim.gaea2.core.utils.SpringUtil;
import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.model.SysUser;
import com.tim.gaea2.domain.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/5/9.
 */
public class LoginUser implements UserDetails {

    private SysUser sysUser;

    public LoginUser(SysUser sysUser){
        this.sysUser = sysUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<UserRolePO> list =sysUser.getAllUserRolePOs();
        for (UserRolePO po : list){
            auths.add(new SimpleGrantedAuthority(po.getRoleId().toString()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUserName();
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
