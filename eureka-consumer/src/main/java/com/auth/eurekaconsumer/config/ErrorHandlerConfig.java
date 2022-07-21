package com.auth.eurekaconsumer.config;

import com.auth.model.CauseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandlerConfig {
    @Bean
    public CauseHandler causeHandler() {
        return new CauseHandler();
    }
}