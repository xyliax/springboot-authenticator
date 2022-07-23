package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
    private String userRole;
}
