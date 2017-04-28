package com.tim.gaea2.core.config;

import org.dozer.DozerBeanMapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/4/28.
 */
@Configuration
@ComponentScan(basePackages={"com.tim.gaea2"})
public class DozerMappingConfiguration {

    @Bean(name = "org.dozer.Mapper")
    public DozerBeanMapper dozerBean() {
        List<String> mappingFiles = Arrays.asList(
                "dozer-global-configuration.xml",
                "dozer-bean-mappings.xml",
                "more-dozer-bean-mappings.xml"
        );

        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }

    @Bean(name = "dozerBean")
    public DozerBeanMapperFactoryBean configDozer() throws IOException {
        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:dozer-bean-mappings.xml");
        mapper.setMappingFiles(resources);
        return mapper;
    }
}
