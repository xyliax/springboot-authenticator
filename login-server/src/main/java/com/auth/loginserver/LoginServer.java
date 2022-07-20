package com.auth.loginserver;

import org.bson.codecs.ObjectIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class LoginServer {
    public static void main(String[] args) {
        SpringApplication.run(LoginServer.class, args);
    }

    @Bean
        //mongoDB的未知问题，无法通过@Id自动生成_id，手动配置
    ObjectIdGenerator objectIdGenerator() {
        return new ObjectIdGenerator();
    }
}
