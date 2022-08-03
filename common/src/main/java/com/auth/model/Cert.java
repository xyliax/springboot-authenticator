package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cert {
    String userId;
    String courseId;
    HashMap<String, String> certInfo;
}

