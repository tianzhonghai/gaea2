package com.tim.gaea2.core.shiro;

import com.tim.gaea2.core.utils.SecretUtils;
import com.tim.gaea2.domain.entity.RoleAndPermissionPO;
import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.model.SysUser;
import com.tim.gaea2.domain.service.RoleService;
import com.tim.gaea2.domain.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tianzhonghai on 2017/5/9.
 */
@Component
public class CustomAuthorizingRealm extends AuthorizingRealm {
    public CustomAuthorizingRealm(){
        super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);

        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
    }

    @Autowired
    private UserInfoService userService;

    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser dbUser = userService.getUserVoByUserName(user.getUserName());
        Set<String> shiroPermissions = new HashSet<>();
        Set<String> roleSet = new HashSet<>();

        List<RoleAndPermissionPO> roles = roleService.getRolePermissionByUserId(dbUser.getId());
        for (RoleAndPermissionPO  roleAndPermissionPO : roles) {
            if(! roleSet.contains(roleAndPermissionPO.getRoleId().toString())) {
                roleSet.add(roleAndPermissionPO.getRoleId().toString());
            }

            shiroPermissions.add(roleAndPermissionPO.getPermissionSign());
        }

        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(shiroPermissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        SysUser user = userService.getUserVoByUserName(username);
        String password = new String((char[]) token.getCredentials());

        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 密码错误
        if (!SecretUtils.MD5(password).equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
//        // 账号锁定
//        if (user.getLocked() == 1) {
//            throw new LockedAccountException("账号已被锁定,请联系管理员");
//        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());

        return info;
    }
}
