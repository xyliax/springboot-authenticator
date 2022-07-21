package com.auth.utilities;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class CauseHandler extends DefaultResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) {

    }
}
