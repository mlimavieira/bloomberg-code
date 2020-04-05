package com.bloomberg.client.feign.operation.dto;

import lombok.Data;

@Data
public class CoreServiceResultResponse {

    private String requestId;
    private String message;
    private Double total;
}
