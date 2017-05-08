package com.tim.gaea2.core.config;

import com.tim.gaea2.core.security.MyFilterSecurityInterceptor;
import com.tim.gaea2.core.utils.SecretUtils;
import com.tim.gaea2.core.security.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by tianzhonghai on 2017/5/4.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private MyFilterSecurityInterceptor mySecurityFilter;

    @Bean
    UserDetailsService customUserService(){
        return new SysUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService())
        .passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return SecretUtils.MD5((String)charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equalsIgnoreCase(SecretUtils.MD5((String)charSequence));
            }
        })
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class)
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                //.successHandler()
            .and()
            .logout().permitAll().invalidateHttpSession(true);
    }

//    @Bean
//    public LoginSuccessHandler loginSuccessHandler(){
//        return new LoginSuccessHandler();
//    }
}
