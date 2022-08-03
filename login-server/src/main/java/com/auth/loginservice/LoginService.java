package com.auth.loginservice;

import com.auth.dao.MongoRepository;
import com.auth.defenum.Cause;
import com.auth.model.User;
import com.auth.util.ServiceSegment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class LoginService {
    @Resource
    private MongoRepository mongoRepository;

    public ServiceSegment userRegister(User user, boolean register) {
        try {
            if (register) {
                User newUser = mongoRepository.createUser(user);
                return new ServiceSegment(newUser);
            } else {
                User newUser = mongoRepository.updateUser(user);
                if (newUser == null)
                    return new ServiceSegment(Cause.NO_RESULT);
                return new ServiceSegment(newUser);
            }
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
                    resUser = mongoRepository.readUserByName(username);
                    break;
                case "other":

                default:
                    return new ServiceSegment(Cause.UNDEF_ARG);
            }
            if (resUser == null)
                return new ServiceSegment(Cause.NO_RESULT);
            if (Objects.equals(password, resUser.getPassword()))
                return new ServiceSegment(resUser);
            else
                return new ServiceSegment(Cause.MISMATCH);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
