package com.auth.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InfoWrapper<T> {
    public ResponseEntity<T> wrap(T info) {
        HttpStatus status = Cause.isCause(info) ?
                HttpStatus.NOT_ACCEPTABLE : HttpStatus.OK;
        return new ResponseEntity<>(info, status);
    }
}
