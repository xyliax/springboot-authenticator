package com.auth.authservice;

import com.auth.dao.MongoRepository;
import com.auth.defenum.Cause;
import com.auth.defenum.Role;
import com.auth.model.Course;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    @Resource
    private MongoRepository mongoRepository;

    public ServiceSegment getUserDetails(String identifier, String type) {
        try {
            User resUser;
            switch (type) {
                case "username":
                    resUser = mongoRepository.readUserByName(identifier);
                    break;
                case "userid":
                    resUser = mongoRepository.readUserById(identifier);
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

    public ServiceSegment assignAuth(String userId, String courseId,
                                     String role, Boolean action) {
        try {
            User user = mongoRepository.readUserById(userId);
            Course course = mongoRepository.readCourseById(courseId);
            boolean stat;
            if (user == null || course == null)
                return new ServiceSegment(Cause.NO_RESULT);
            if (action)
                stat = user.addPermission(Role.valueOf(role), courseId);
            else
                stat = user.delPermission(Role.valueOf(role), courseId);
            return new ServiceSegment(Boolean.toString(stat));
        } catch (IllegalArgumentException illegalArgumentException) {
            return new ServiceSegment(Cause.UNDEF_ARG);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment getUserListByRole(String roleStr) {
        try {
            List<User> userList;
            if ("*".equals(roleStr))
                userList = mongoRepository.readUserAll();
            else {
                Role role = Role.valueOf(roleStr);
                userList = mongoRepository.readUserByRole(role);
            }
            return new ServiceSegment(userList.toArray(User[]::new));
        } catch (IllegalArgumentException illegalArgumentException) {
            return new ServiceSegment(Cause.UNDEF_ARG);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment getUserListByAuth(String courseId, String roleStr) {
        try {
            Course course = mongoRepository.readCourseById(courseId);
            List<User> userList = mongoRepository.readUserAll();
            Role role = Role.valueOf(roleStr);
            if (!Course.isValid(course))
                return new ServiceSegment(Cause.NO_RESULT);
            List<User> resList = new ArrayList<>();
            userList.forEach(user -> {
                if (user.hasPermission(role, courseId)) resList.add(user);
            });
            return new ServiceSegment(resList);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new ServiceSegment(Cause.UNDEF_ARG);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
