package com.tim.gaea2.core.config;

import com.tim.gaea2.core.shiro.CustomAuthorizingRealm;
import com.tim.gaea2.core.shiro.CustomHashedCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tianzhonghai on 2017/5/9.
 */
@Configuration
public class ShiroConfig {
    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

    @Bean(name = "ShiroRealm")
    public CustomAuthorizingRealm getShiroRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        CustomAuthorizingRealm shiroRealm = new CustomAuthorizingRealm();
        shiroRealm.setCredentialsMatcher(matcher);
        return shiroRealm;
    }

//    @Bean(name = "shiroEhcacheManager")
//    public CustomCacheManager getGuavaCacheManager() {
//        CustomCacheManager em = new CustomCacheManager();
//        return em;
//    }

    @Bean(name="credentialsMatcher")
    public CustomHashedCredentialsMatcher getCredentialsMatcher(){
        CustomHashedCredentialsMatcher platFormCredentialsMatcher = new CustomHashedCredentialsMatcher();
        platFormCredentialsMatcher.setHashAlgorithmName("MD5");
        platFormCredentialsMatcher.setHashIterations(2);
        platFormCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return platFormCredentialsMatcher;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("ShiroRealm") CustomAuthorizingRealm shiroRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(shiroRealm);
        //dwsm.setCacheManager(getEhCacheManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager")DefaultWebSecurityManager dwsm) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(dwsm);
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager dwsm) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean
                .setSecurityManager(dwsm);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
        filterChainDefinitionMap.put("/login", "authc");
        filterChainDefinitionMap.put("/BJUI/**", "anon");
        filterChainDefinitionMap.put("/platform/**", "anon");
        filterChainDefinitionMap.put("/admin/course/category/list", "perms[user:view]");
        filterChainDefinitionMap.put("/admin/course/category/edit", "perms[user:update]");
        filterChainDefinitionMap.put("/admin/course/category/update", "perms[user:update]");
        filterChainDefinitionMap.put("/admin/course/category/add", "perms[user:add]");
        filterChainDefinitionMap.put("/admin/**", "anon");
        shiroFilterFactoryBean
                .setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * FilterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

}
