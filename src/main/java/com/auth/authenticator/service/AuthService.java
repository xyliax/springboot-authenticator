package com.auth.authenticator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private MicroServiceUrl microServiceUrl;

}
