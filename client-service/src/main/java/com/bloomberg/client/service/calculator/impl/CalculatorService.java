package com.bloomberg.client.service.calculator.impl;

import com.bloomberg.client.exception.ServiceIntegrationException;
import com.bloomberg.client.feign.operation.OperationServiceClient;
import com.bloomberg.client.feign.operation.dto.CoreServiceOperationRequest;
import com.bloomberg.client.feign.operation.dto.CoreServiceResultResponse;
import com.bloomberg.client.model.OperationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

/**
 * Integration class that integrate with the Core Calculator system
 */
@Slf4j
@Service
public class CalculatorService implements com.bloomberg.client.service.calculator.CalculatorService {


    @Autowired
    OperationServiceClient operServiceClient;


    public OperationResult sum(Double... values) {

        CoreServiceOperationRequest operRequest = new CoreServiceOperationRequest.Builder().sum()
                .addValues(Arrays.asList(values))
                .build();

        CoreServiceResultResponse resultResponse = operServiceClient.operation(operRequest);

        if(resultResponse != null) {
            OperationResult result = new OperationResult();
            result.setId(UUID.randomUUID().toString());
            result.setTotal(resultResponse.getTotal());
            result.setNumbers(Arrays.asList(values));

            return result;
        }

        throw new ServiceIntegrationException(HttpStatus.BAD_REQUEST, "Something went wrong retrieving the result");
    }
}
