package com.tim.gaea2.core.security;

import com.tim.gaea2.core.utils.SpringUtil;
import com.tim.gaea2.domain.entity.RolePermissionPO;
import com.tim.gaea2.domain.entity.RolePermissionWithUrlPO;
import com.tim.gaea2.domain.service.RoleService;
import com.tim.gaea2.domain.service.UserInfoService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * Created by Tim on 2017/5/7.
 */
@Service
public class CustomInvocationSecurityMetadataSourceService
        implements FilterInvocationSecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        RoleService roleService = SpringUtil.getBean(RoleService.class);
        List<RolePermissionWithUrlPO> rolePermissionPOs = roleService.getAllRoleResources();
        FilterInvocation filterInvocation = (FilterInvocation) o;

        for (RolePermissionWithUrlPO po : rolePermissionPOs){
            String resURL = po.getPermissionSign();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
            if(requestMatcher.matches(filterInvocation.getHttpRequest())) {
                //return resourceMap.get(resURL);
            }

        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
