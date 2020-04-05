package com.bloomberg.core.service;

import com.bloomberg.core.model.OperationType;

import java.util.List;

public interface CalculatorService {

    Double calculate(OperationType type, List<Double> values);
}
