package com.tim.gaea2.core.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 *在验证部分简略提过了，所有的Authentication实现需要保存在一个GrantedAuthority对象数组中。
 *这就是赋予给主体的权限。 GrantedAuthority对象通过AuthenticationManager
 *保存到 Authentication对象里，然后从AccessDecisionManager读出来，进行授权判断。
 *
 *Spring Security提供了一些拦截器，来控制对安全对象的访问权限，例如方法调用或web请求。
 *一个是否允许执行调用的预调用决定，是由AccessDecisionManager实现的。
 *这个 AccessDecisionManager 被AbstractSecurityInterceptor调用，
 *它用来作最终访问控制的决定。 这个AccessDecisionManager接口包含三个方法：
 */
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager {
    /**
     * AccessDecisionManager使用方法参数传递所有信息，这好像在认证评估时进行决定。
     特别是，在真实的安全方法期望调用的时候，传递安全Object启用那些参数。
     比如，让我们假设安全对象是一个MethodInvocation。
     很容易为任何Customer参数查询MethodInvocation，
     然后在AccessDecisionManager里实现一些有序的安全逻辑，来确认主体是否允许在那个客户上操作。
     如果访问被拒绝，实现将抛出一个AccessDeniedException异常。
     * @param authentication
     * @param o
     * @param collection
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if( collection == null ) {
            return ;
        }

        Iterator<ConfigAttribute> ite = collection.iterator();

        while( ite.hasNext()){
            ConfigAttribute ca = ite.next();
            String needRole = ((SecurityConfig)ca).getAttribute().trim();

            //ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
            for( GrantedAuthority ga: authentication.getAuthorities()){
                String userOwnRole = ga.getAuthority().trim();
                if(needRole.equals(userOwnRole)){
                    return;
                }
            }
        }

        throw new AccessDeniedException("权限不足");
    }

    /**
     * 方法在启动的时候被AbstractSecurityInterceptor调用，来决定AccessDecisionManager是否可以执行传递ConfigAttribute。
     * @param configAttribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * 方法被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型。
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
