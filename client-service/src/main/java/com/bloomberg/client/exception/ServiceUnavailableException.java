package com.bloomberg.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceUnavailableException extends ResponseStatusException {

    public ServiceUnavailableException(HttpStatus status, String message) {
        super(status, message);
    }

    public ServiceUnavailableException(HttpStatus status, String message, Throwable e) {
        super(status, message, e);
    }
}
