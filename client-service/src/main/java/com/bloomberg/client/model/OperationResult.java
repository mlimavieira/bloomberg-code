package com.bloomberg.client.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OperationResult {

    private String id;
    private Double total;

    private List<Double> numbers = new ArrayList<>();
}
