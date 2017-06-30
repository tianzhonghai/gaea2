package com.tim.gaea2.core.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tianzhonghai on 2017/6/30.
 */
@Configuration
public class CacheConfig {

    @Bean(name = "defaultCache")
    public ICaching getDefaultCache() {
        return new GuavaCache();
    }

}
