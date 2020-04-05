package com.bloomberg.client.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private String details;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
