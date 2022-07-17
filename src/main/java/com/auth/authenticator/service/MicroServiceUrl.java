package com.auth.authenticator.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "url")
public class MicroServiceUrl {
    private String loginServerUrl;
    private String authServerUrl;
    private String contentServerUrl;
}
