package com.bloomberg.core.controller;


import com.bloomberg.core.model.ErrorMessageResponse;
import com.bloomberg.core.model.OperationRequest;
import com.bloomberg.core.model.OperationType;
import com.bloomberg.core.model.ResultResponse;
import com.bloomberg.core.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/operation")
public class OperationController {

    private CalculatorService calculatorService;

    public OperationController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ErrorMessageResponse illegalArgumentException(IllegalArgumentException ex) {

        return new ErrorMessageResponse(400, "Validation Error", Arrays.asList(ex.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorMessageResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Iterator<ObjectError> errorsIterator = ex.getBindingResult().getAllErrors().iterator();
        List<String> details = new ArrayList<>();
        while(errorsIterator.hasNext()) {
            ObjectError error = (ObjectError)errorsIterator.next();
            details.add(error.getDefaultMessage());
        }
        return new ErrorMessageResponse(400, "Validation Error", details);
    }

    @PostMapping
    public ResultResponse operation(@RequestBody @Valid OperationRequest operationRequest) {

        log.info("Request received id: {}", operationRequest.getRequestId());
        OperationType type = OperationType.getByName(operationRequest.getOperation());
        Double result = calculatorService.calculate(type, operationRequest.getValues());

        ResultResponse response = new ResultResponse();
        response.setTotal(result);
        return response;
    }
}
