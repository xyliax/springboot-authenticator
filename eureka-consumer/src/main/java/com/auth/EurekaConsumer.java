package com.auth;

import com.auth.config.LoadBalanceConfig;
import com.auth.model.User;
import com.auth.util.CauseErrorHandler;
import com.auth.util.InfoWrapper;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients({
        @LoadBalancerClient(name = "login-server", configuration = LoadBalanceConfig.class),
        @LoadBalancerClient(name = "auth-server", configuration = LoadBalanceConfig.class)}
)
public class EurekaConsumer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumer.class, args);
    }

    @Resource
    private CauseErrorHandler causeHandler;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(causeHandler);
        return restTemplate;
    }

    @Bean
    ObjectIdGenerator objectIdGenerator() {
        //mongoDB的未知问题，无法通过@Id自动生成_id，手动配置
        return new ObjectIdGenerator();
    }

    @Bean
    InfoWrapper<String> stringInfoWrapper() {
        return new InfoWrapper<>(String.class);
    }

    @Bean
    InfoWrapper<User> userInfoWrapper() {
        return new InfoWrapper<>(User.class);
    }
}
