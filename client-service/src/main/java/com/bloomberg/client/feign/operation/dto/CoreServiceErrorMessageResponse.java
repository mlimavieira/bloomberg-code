package com.bloomberg.client.feign.operation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CoreServiceErrorMessageResponse {

    private int code;
    private String message;

    private List<String> details;
}
