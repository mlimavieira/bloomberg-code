package com.bloomberg.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ApiModel(description = "Operation Request to perform the calculation")
public class OperationRequest {


    private String requestId = UUID.randomUUID().toString();

    @ApiModelProperty(notes = "Operation Type. Valid values are ADD,SUB, DIV, MULT")
    @Size( min = 3, message = "Operation type is required")
    private String operation;

    @ApiModelProperty(notes = "List of numbers to perform the operation")
    @NotEmpty(message = "Value is required")
    @Size(min=2, message = "Value must have at least 2 values are required.")
    private List<Double> values = new ArrayList<>();

}
