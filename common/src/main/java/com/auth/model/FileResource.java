package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResource {
    private String filename;
    private long size;
    private String contentType;
    private byte[] bytes;
    private String description;
}
