package com.auth.loginservice;

import com.auth.dao.UserRepository;
import com.auth.model.Cause;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public ServiceSegment userRegister(User user) {
        try {
            User newUser = userRepository.createUser(user);
            return new ServiceSegment(newUser.getUserId());
        } catch (DuplicateKeyException duplicateKeyException) {
            return new ServiceSegment(Cause.DUP_NAME);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment userLogin(String username, String password, String type) {
        try {
            User resUser;
            switch (type) {
                case "username":
                    resUser = userRepository.readUserByName(username);
                    break;
                case "other":

                default:
                    return new ServiceSegment(Cause.UNDEF_ARG);
            }
            if (resUser == null)
                return new ServiceSegment(Cause.NO_RESULT);
            if (Objects.equals(password, resUser.getPassword()))
                return new ServiceSegment(resUser.getEncryptD());
            else
                return new ServiceSegment(Cause.MISMATCH);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
