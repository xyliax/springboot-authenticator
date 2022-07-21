package com.auth.eurekaconsumer;

import com.auth.eurekaconsumer.config.LoadBalanceConfig;
import com.auth.utilities.CauseHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClient(name = "login-server", configuration = LoadBalanceConfig.class)
public class EurekaConsumer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumer.class, args);
    }

    @Resource
    private CauseHandler causeHandler;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(causeHandler);
        return restTemplate;
    }
}
