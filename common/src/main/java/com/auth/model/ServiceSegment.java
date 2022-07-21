package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceSegment {
    private Cause cause;
    private Object content;

    public ServiceSegment(Cause cause) {
        this(cause, "#Check Response Header#");
    }

    public ServiceSegment(Object content) {
        this(Cause.SUCCESS, content);
    }
}