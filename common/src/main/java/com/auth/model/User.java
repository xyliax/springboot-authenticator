package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@AllArgsConstructor
@Document(collection = "USERS")
@CompoundIndexes({@CompoundIndex(name = "username_password_idx",
        def = "{'username': 1, 'password': 1}", unique = true)})
public class User {
    @Id
    @Field(targetType = FieldType.STRING)
    private String userId;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String publicKy;
    private String password;
    private String EncryptD;
}
