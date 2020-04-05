package com.bloomberg.client.controller;

import com.bloomberg.client.controller.dto.ErrorResponse;
import com.bloomberg.client.exception.ServiceIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {

        log.error("Validation error. Message: {}", ex.getMessage());
        ErrorResponse errorMessageResponse = new ErrorResponse("Error during validation", ex.getMessage());
        return handleExceptionInternal(ex, errorMessageResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {

        log.error("Unexpect exception", ex);
        ErrorResponse errorMessageResponse = new ErrorResponse("Unexpected exception", ex.getMessage());
        return handleExceptionInternal(ex, errorMessageResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = ServiceIntegrationException.class)
    protected ResponseEntity<Object> handleServiceIntegration(ServiceIntegrationException ex, WebRequest request) {

        log.error("Error on Service Integration - HttpStatus: {} Message: {}", ex.getStatus(), ex.getMessage());
        ErrorResponse errorMessageResponse = new ErrorResponse(ex.getReason(), ex.getMessage());
        return handleExceptionInternal(ex, errorMessageResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {ConnectException.class})
    protected ResponseEntity<Object> handleConnectException(ConnectException ex, WebRequest request) {
        log.error("Error to connect on Service Integration not available. Message: {}", ex.getMessage());
        ErrorResponse errorMessageResponse = new ErrorResponse("Fail to connect to Calculator Core", ex.getMessage());
        return handleExceptionInternal(ex, errorMessageResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {TimeoutException.class})
    protected ResponseEntity<Object> handleTimeoutException(TimeoutException ex, WebRequest request) {
        log.error("Timeout to connect on Service Integration not available. Message: {}", ex.getMessage());
        ErrorResponse errorMessageResponse = new ErrorResponse("Fail to connect to Calculator Core", ex.getMessage());
        return handleExceptionInternal(ex, errorMessageResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
