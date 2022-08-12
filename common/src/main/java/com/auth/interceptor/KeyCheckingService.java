package com.auth.interceptor;

import com.auth.dao.MongoRepository;
import com.auth.defenum.Role;
import com.auth.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Service
@RestController
public class KeyCheckingService {
    @Resource
    private MongoRepository mongoRepository;

    public Role getRole(String remote, String encryptD) {
        if (remote == null || encryptD == null)
            return Role.UNKNOWN;
        User user = mongoRepository.readUserById(remote);
        if (!user.getEncryptD().equals(encryptD))
            return Role.UNKNOWN;
        return user.getUserRole();
    }
}
