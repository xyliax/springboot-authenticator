package com.auth.config;

import org.bson.codecs.ObjectIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoIdConfig {
    @Bean
    ObjectIdGenerator objectIdGenerator() {
        return new ObjectIdGenerator();
    }
}
