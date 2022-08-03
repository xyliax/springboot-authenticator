package com.auth.model;

import com.auth.defenum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableMongoAuditing
@Document(collection = "USERS")
public class User {
    @MongoId(targetType = FieldType.STRING)
    private String userId;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String publicKy;
    private String password;
    private String encryptD;
    private Role userRole;
    private HashMap<Role, HashSet<String>> permissions;

    public void addPermission(Role role, String id) {
        if (!permissions.containsKey(role))
            permissions.put(role, new HashSet<>());
        permissions.get(role).add(id);
    }

    public void delPermission(Role role, String id) {
        if (permissions.containsKey(role)) {
            permissions.get(role).remove(id);
        }
    }

    public boolean hasPermission(Role role, String id) {
        return permissions.containsKey(role) && permissions.get(role).contains(id);
    }
}
