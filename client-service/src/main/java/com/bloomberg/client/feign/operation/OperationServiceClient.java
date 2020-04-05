package com.bloomberg.client.feign.operation;


import com.bloomberg.client.feign.operation.dto.CoreServiceOperationRequest;
import com.bloomberg.client.feign.operation.dto.CoreServiceResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "CALCULATOR-SERVICE-CORE")
public interface OperationServiceClient {

    @PostMapping("/operation")
    CoreServiceResultResponse operation(@RequestBody @Valid CoreServiceOperationRequest operationRequest);


}
