package com.auth.config;

import com.auth.util.InfoWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoWrapperConfig {
    @Bean
    public InfoWrapper infoWrapper() {
        return new InfoWrapper();
    }
}
