package com.tim.gaea2.core.config;

import com.tim.gaea2.core.security.CustomSecurityInterceptorFilter;
import com.tim.gaea2.core.security.CustomUserDetailsService;
import com.tim.gaea2.core.security.LoginSuccessHandler;
import com.tim.gaea2.core.utils.SecretUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private CustomSecurityInterceptorFilter customSecurityInterceptorFilter;

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserDetailsService();
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
        http.addFilterBefore(customSecurityInterceptorFilter, FilterSecurityInterceptor.class)
            .authorizeRequests()
            .antMatchers("/css/**","/images/**","/js/**").permitAll()
             .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").failureUrl("/login?msg=fail").permitAll()
                //.successHandler()
            .and()
            .logout().permitAll().invalidateHttpSession(true);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }
}
