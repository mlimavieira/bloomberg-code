package com.bloomberg.client.service.calculator;

import com.bloomberg.client.exception.ServiceIntegrationException;
import com.bloomberg.client.feign.operation.OperationServiceClient;
import com.bloomberg.client.feign.operation.dto.CoreServiceResultResponse;
import com.bloomberg.client.model.OperationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class CalculatorServiceTest {

    @MockBean
    OperationServiceClient operationServiceClient;

    @Autowired
    private CalculatorService calculatorService;


    @Test
    void sumSuccess() {

        CoreServiceResultResponse response = new CoreServiceResultResponse();
        response.setMessage("Dummy");
        response.setTotal(3.0);

        Mockito.when(operationServiceClient.operation(Mockito.any())).thenReturn(response);
        OperationResult result = calculatorService.sum(1.0, 2.0);

        assertThat(result).isNotNull();
        assertThat(result.getTotal()).isEqualTo(3.0);
        assertThat(result.getNumbers()).isNotEmpty();
        assertThat(result.getNumbers()).contains(1.0, 2.0);
    }


    @Test
    void sumFail() {

        CoreServiceResultResponse response = new CoreServiceResultResponse();
        response.setMessage("Dummy");
        response.setTotal(3.0);

        Mockito.when(operationServiceClient.operation(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(ServiceIntegrationException.class, () -> {
            OperationResult result = calculatorService.sum(1.0, 2.0);
        });


    }
}