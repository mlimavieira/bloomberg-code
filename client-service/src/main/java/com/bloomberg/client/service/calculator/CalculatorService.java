package com.bloomberg.client.service.calculator;

import com.bloomberg.client.model.OperationResult;
import org.springframework.cache.annotation.Cacheable;


public interface CalculatorService {

    /**
     * Perform the calculation calling the Core calculation service.
     * @param values - List of double values
     * @return OperationResult
     */
    @Cacheable(value = "operations")
    OperationResult sum(Double... values);
}
