package com.tim.gaea2.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by tianzhonghai on 2017/3/13.
 */
@SpringBootApplication
@ComponentScan("com.tim.gaea2")
//@ImportResource("classpath:applicationContext.xml")
//@EnableCaching
//@EnableAspectJAutoProxy
//@EnableAutoConfiguration
//@EnableScheduling
//@EnableAsync
//@EnableWebMvc
public class WebApplication {
    public static void main(String[] args){
        SpringApplication.run(WebApplication.class,args);
    }
}