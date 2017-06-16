package com.tim.gaea2.core.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tianzhonghai on 2017/6/16.
 */
@Configuration
public class GaeaConfig {
    @Bean
    public GaeaDialect gaeaDialect() {
        return new GaeaDialect();
    }
}
