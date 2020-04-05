package com.bloomberg.core.service.impl;

import com.bloomberg.core.model.OperationType;
import com.bloomberg.core.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {


    public Double calculate(OperationType type, List<Double> values) {

        if(values == null || values.size() < 2) {
            throw new IllegalArgumentException("Value is required");
        }

        if(type == null) {
            throw new IllegalArgumentException("Invalid operation.");
        }

        log.info("Start calculation");
        switch (type) {
            case ADD:
                return add(values);
            case SUB:
                return subtract(values);
            case DIV:
                return divide(values);
            case MULT:
                return multiply(values);
            default:

                log.warn("Invalid operation. Operation: {}", type);

                throw new IllegalArgumentException("Invalid operation.");

        }
    }

    private Double add(List<Double> values) {
        log.debug("Performing ADD operation: {}", values);
        Iterator<Double> iterator = values.iterator();
        Double result = iterator.next();

        while(iterator.hasNext()) {
            result += iterator.next();
        }
        return result;
    }

    private Double subtract(List<Double> values) {
        log.debug("Performing SUBTRACT operation: {}", values);

        Iterator<Double> iterator = values.iterator();
        Double result = iterator.next();

        while(iterator.hasNext()) {
            result -= iterator.next();
        }

        return result;
    }

    private Double multiply(List<Double> values) {
        log.debug("Performing MULTIPLY operation: {}", values);

        Iterator<Double> iterator = values.iterator();
        Double result = iterator.next();

        while(iterator.hasNext()) {
            result *= iterator.next();
        }

        return result;
    }

    private Double divide(List<Double> values) {
        log.debug("Performing DIVIDE operation: {}", values);

        Iterator<Double> iterator = values.iterator();
        Double result = iterator.next();

        while(iterator.hasNext()) {
            result /= iterator.next();
        }
        return result;
    }
}
