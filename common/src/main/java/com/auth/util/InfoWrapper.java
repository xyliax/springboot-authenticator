package com.auth.util;

import com.auth.defenum.Cause;
import com.auth.model.ServiceSegment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InfoWrapper<T> {
    private final Class<T> tClass;

    public InfoWrapper(Class<T> tClass) {
        this.tClass = tClass;
    }

    public ResponseEntity<T> wrap(ServiceSegment segment) {
        Cause cause = segment.getCause();
        Object content = segment.getContent();
        HttpStatus status = Cause.isFailure(cause) ?
                HttpStatus.NOT_ACCEPTABLE : HttpStatus.OK;
        HttpHeaders header = new HttpHeaders();
        header.add("Cause", cause.code);
        return new ResponseEntity<>(tClass.cast(content), header, status);
    }
}
