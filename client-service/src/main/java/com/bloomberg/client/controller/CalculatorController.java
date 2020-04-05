package com.bloomberg.client.controller;


import com.bloomberg.client.controller.dto.ResultResponse;
import com.bloomberg.client.model.OperationResult;
import com.bloomberg.client.service.calculator.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/add")
    public ResultResponse sum(@RequestBody Double ... values) {

        OperationResult result = calculatorService.sum(values);

        return buildResultResponse(result);
    }

    private ResultResponse buildResultResponse(OperationResult result) {
        ResultResponse response = new ResultResponse();
        response.setNumbers(result.getNumbers());
        response.setResult(result.getTotal());

        return response;
    }
}
