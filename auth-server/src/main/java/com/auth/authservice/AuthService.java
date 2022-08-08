package com.auth.authservice;

import com.auth.dao.MongoRepository;
import com.auth.defenum.Cause;
import com.auth.defenum.Role;
import com.auth.model.Course;
import com.auth.model.User;
import com.auth.util.ServiceSegment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public ServiceSegment deleteUser(String userId) {
        try {
            User user = mongoRepository.deleteUserById(userId);
            if (user == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(user);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment assignAuth(String userId, Map<String, String>[] idMapArray) {
        try {
            User user = mongoRepository.readUserById(userId);
            if (user == null)
                return new ServiceSegment(Cause.NO_RESULT);
            for (Map<String, String> idMap : idMapArray) {
                String courseId = idMap.get("courseId");
                Role role = Role.parse(idMap.get("role"));
                String actionStr = idMap.get("action");
                boolean action;
                if ("true".equalsIgnoreCase(actionStr))
                    action = true;
                else if ("false".equalsIgnoreCase(actionStr))
                    action = false;
                else continue;
                if (action)
                    user.addPermission(role, courseId);
                else
                    user.delPermission(role, courseId);
            }
            User userSaved = mongoRepository.updateUser(user);
            return new ServiceSegment(userSaved);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment getUserList() {
        try {
            List<User> userList = mongoRepository.readUserAll();
            return new ServiceSegment(userList.toArray(User[]::new));
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment getUserListByRole(String courseId, String roleGroup) {
        try {
            List<User> userList = mongoRepository.readUserAll();
            Course course = mongoRepository.readCourseById(courseId);
            Role[] roles;
            if (course == null)
                return new ServiceSegment(Cause.UNDEF_ARG);
            switch (roleGroup) {
                case "view":
                    roles = Role.viewer();
                    break;
                case "edit":
                    roles = Role.editor();
                    break;
                default:
                    return new ServiceSegment(Cause.UNDEF_ARG);
            }
            List<User> resList = new ArrayList<>();
            userList.forEach(user -> {
                if (course.permit(user, roles)) resList.add(user);
            });
            return new ServiceSegment(resList.toArray(User[]::new));
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
