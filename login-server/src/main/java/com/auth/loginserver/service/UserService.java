package com.auth.loginserver.service;

import com.auth.loginserver.dao.UserRepository;
import com.auth.model.Cause;
import com.auth.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public String userRegister(User user) {
        try {
            User newUser = userRepository.createUser(user);
            return newUser.getUserId();
        } catch (DuplicateKeyException duplicateKeyException) {
            return Cause.DUP_NAME.code;
        } catch (RuntimeException runtimeException) {
            return Cause.UNKNOWN.code;
        }
    }

    public String userLogin(User user) {
        try {
            User resUser = userRepository.readUser(user);
            if (Objects.equals(user.getPassword(), resUser.getPassword()))
                return resUser.getEncryptD();
            else return Cause.MISMATCH.code;
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return Cause.NO_RESULT.code;
        } catch (RuntimeException runtimeException) {
            return Cause.UNKNOWN.code;
        }
    }
}
