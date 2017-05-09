package com.tim.gaea2.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by tianzhonghai on 2017/5/8.
 */
@Component
public class CustomSecurityInterceptorFilter extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private CustomFilterInvocationSecurityMetadataSource  mySecurityMetadataSource;

    @Autowired
    private CustomAccessDecisionManager myAccessDecisionManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void init(){
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.mySecurityMetadataSource;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fileInvocation  = new FilterInvocation( servletRequest, servletResponse, filterChain );

        invoke(fileInvocation);
    }

    public void invoke(FilterInvocation fileInvocation) throws IOException, ServletException{
        System.out.println("filter..........................");
        InterceptorStatusToken token = super.beforeInvocation(fileInvocation);
        try{
            fileInvocation.getChain().doFilter(fileInvocation.getRequest(), fileInvocation.getResponse());
        }finally{
            super.afterInvocation(token, null);
        }

    }

    public void destroy() {

    }
}
