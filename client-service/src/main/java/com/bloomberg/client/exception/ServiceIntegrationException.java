package com.bloomberg.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceIntegrationException extends ResponseStatusException {

    public ServiceIntegrationException(HttpStatus status, String message) {
        super(status, message);
    }
}
