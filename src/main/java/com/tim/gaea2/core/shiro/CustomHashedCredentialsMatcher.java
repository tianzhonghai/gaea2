package com.tim.gaea2.core.shiro;

import com.tim.gaea2.domain.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 验证器
 * Created by Tim on 2017/5/9.
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //String userno = (String) token.getPrincipal();
        ShiroUser shiroUser = (ShiroUser)info.getPrincipals().getPrimaryPrincipal();
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // 根据登录名查询用户
//            Subject subject = SecurityUtils.getSubject();
//            Session session = subject.getSession();
//            session.setAttribute("user", shiroUser);
        }
        return matches;
    }




}
