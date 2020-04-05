package com.bloomberg.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorMessageResponse {

    private int code;
    private String message;

    private List<String> details;
}
