package com.auth.util;

import com.auth.defenum.Cause;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InfoWrapper {
    public <T> ResponseEntity<T> wrap(ServiceSegment segment, Class<T> tClass) {
        Cause cause = segment.getCause();
        Object content = segment.getContent();
        HttpStatus status = Cause.isFailure(cause) ?
                HttpStatus.NOT_ACCEPTABLE : HttpStatus.OK;
        HttpHeaders header = new HttpHeaders();
        header.add("Cause", cause.code);
        header.add("CauseStr", cause.name());
        return new ResponseEntity<>(tClass.cast(content), header, status);
    }
}
