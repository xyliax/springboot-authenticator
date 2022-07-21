package com.auth.loginserver.service;

import com.auth.loginserver.dao.UserRepository;
import com.auth.model.Cause;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.Nullable;
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
            return new ServiceSegment(Cause.NO_RESULT);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment userLogin(User user, @Nullable String type) {
        try {
            User resUser = userRepository.readUser(user);
            if (type == null || type.equals("username")) {
                if (Objects.equals(user.getPassword(), resUser.getPassword()))
                    return new ServiceSegment(user.getEncryptD());
                else
                    return new ServiceSegment(Cause.MISMATCH);
            } else return new ServiceSegment(Cause.UNDEF_ARG);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return new ServiceSegment(Cause.NO_RESULT);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
