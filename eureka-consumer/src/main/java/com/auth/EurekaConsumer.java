package com.auth;

import com.auth.config.LoadBalancerConfig;
import com.auth.interceptor.RestInterceptor;
import com.auth.util.CauseErrorHandler;
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
        @LoadBalancerClient(name = "login-server", configuration = LoadBalancerConfig.class),
        @LoadBalancerClient(name = "auth-server", configuration = LoadBalancerConfig.class),
        @LoadBalancerClient(name = "content-server", configuration = LoadBalancerConfig.class)}
)
public class EurekaConsumer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumer.class, args);
    }

    @Resource
    private CauseErrorHandler causeHandler;
    @Resource
    private RestInterceptor restInterceptor;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(causeHandler);
        restTemplate.getInterceptors().add(restInterceptor);
        return restTemplate;
    }
}
