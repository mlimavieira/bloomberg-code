package com.bloomberg.client.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultResponse {

    private String id;
    private Double result;
    private List<Double> numbers;
}
