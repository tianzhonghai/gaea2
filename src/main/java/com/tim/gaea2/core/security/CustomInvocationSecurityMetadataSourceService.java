package com.tim.gaea2.core.security;

import com.tim.gaea2.core.utils.SpringUtil;
import com.tim.gaea2.domain.entity.RolePermissionPO;
import com.tim.gaea2.domain.entity.RolePermissionWithUrlPO;
import com.tim.gaea2.domain.service.RoleService;
import com.tim.gaea2.domain.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * Created by Tim on 2017/5/7.
 */
@Service
public class CustomInvocationSecurityMetadataSourceService
        implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RoleService roleService;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        if (resourceMap == null) {
            loadResourceDefine();
        }

        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
            if(requestMatcher.matches(filterInvocation.getHttpRequest())) {
                return resourceMap.get(resURL);
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

    //一定要加上@PostConstruct注解.被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
    @PostConstruct
    private void loadResourceDefine(){
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        //RoleService roleService = SpringUtil.getBean(RoleService.class);
        List<RolePermissionWithUrlPO> rolePermissionPOs = roleService.getAllRoleResources();

        for (RolePermissionWithUrlPO po : rolePermissionPOs) {
            ConfigAttribute ca = new SecurityConfig(po.getRoleId().toString());

            if(resourceMap.containsKey(po.getPermissionSign())){
                Collection<ConfigAttribute> value = resourceMap.get(po.getPermissionSign());
                value.add(ca);
                resourceMap.put(po.getPermissionSign(), value);

            }
            else {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add(ca);
                resourceMap.put(po.getPermissionSign(), atts);
            }
        }
    }
}
