package com.auth.authservice;

import com.auth.dao.UserRepository;
import com.auth.model.Cause;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public ServiceSegment getUserDetails(String identifier, String type) {
        try {
            User resUser;
            switch (type) {
                case "username":
                    resUser = userRepository.readUserByName(identifier);
                    break;
                case "userid":
                    resUser = userRepository.readUserById(identifier);
                    break;
                default:
                    return new ServiceSegment(Cause.UNDEF_ARG);
            }
            if (resUser == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(resUser);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
