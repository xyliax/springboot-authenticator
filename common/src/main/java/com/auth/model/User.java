package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
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
}
