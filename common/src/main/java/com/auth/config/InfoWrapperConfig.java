package com.auth.config;

import com.auth.model.User;
import com.auth.util.InfoWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoWrapperConfig {
    @Bean
    InfoWrapper<String> stringInfoWrapper() {
        return new InfoWrapper<>(String.class);
    }

    @Bean
    InfoWrapper<User> userInfoWrapper() {
        return new InfoWrapper<>(User.class);
    }

    @Bean
    InfoWrapper<User[]> userArrayInfoWrapper() {
        return new InfoWrapper<>(User[].class);
    }
}
