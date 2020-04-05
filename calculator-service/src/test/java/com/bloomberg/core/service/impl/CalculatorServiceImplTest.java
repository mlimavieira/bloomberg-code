package com.bloomberg.core.service.impl;

import com.bloomberg.core.model.OperationType;
import com.bloomberg.core.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class CalculatorServiceImplTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    void calculateAddTest() {

        Double result = calculatorService.calculate(OperationType.ADD, Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        assertThat(result).isEqualTo(15.0);
    }

    @Test
    void calculateSubTest() {

        Double result = calculatorService.calculate(OperationType.SUB, Arrays.asList(10.0, 1.0, 3.0));
        assertThat(result).isEqualTo(6.0);

    }

    @Test
    void calculateDivTest() {

        Double result = calculatorService.calculate(OperationType.DIV, Arrays.asList(20.0, 2.0, 2.0));
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    void calculateMultTest() {

        Double result = calculatorService.calculate(OperationType.MULT, Arrays.asList(10.0, 2.0, 3.0));
        assertThat(result).isEqualTo(60.0);
    }


    @Test()
    void calculateInvalidValuesTest() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.calculate(OperationType.ADD, null);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.calculate(OperationType.ADD,  Arrays.asList(10.0));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.calculate(null, Arrays.asList(10.0, 2.0));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.calculate(OperationType.UNKNOWN, Arrays.asList(10.0, 2.0));
        });
    }
}