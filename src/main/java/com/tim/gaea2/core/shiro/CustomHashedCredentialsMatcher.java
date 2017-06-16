package com.tim.gaea2.core.shiro;

import com.tim.gaea2.domain.model.SysMenu;
import com.tim.gaea2.domain.model.SysSubMenu;
import com.tim.gaea2.domain.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;
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
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();

            List<SysMenu> menus = new ArrayList<>();
            SysMenu menu = new SysMenu();
            menu.setMenuId(1);
            menu.setMenuName("用户管理");
            menu.setUrl("");

            SysSubMenu submenu = new SysSubMenu();
            submenu.setMenuId(2);
            submenu.setMenuName("用户查询");
            submenu.setParentMenuId(1);
            submenu.setUrl("/user/index");
            menu.getSubMenus().add(submenu);

            menus.add(menu);

            session.setAttribute("leftMenu", menus);
        }
        return matches;
    }




}
