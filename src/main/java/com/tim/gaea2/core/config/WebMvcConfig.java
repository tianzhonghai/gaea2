package com.tim.gaea2.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tianzhonghai on 2017/5/4.
 */
@Configuration
@ComponentScan("com.tim.gaea2.web.controllers")
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
