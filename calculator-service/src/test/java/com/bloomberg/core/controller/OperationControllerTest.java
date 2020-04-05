package com.bloomberg.core.controller;

import com.bloomberg.core.model.OperationRequest;
import com.bloomberg.core.model.OperationType;
import com.bloomberg.core.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    void exceptionHandlerIllegalArgumentExceptionTest() throws Exception {

        OperationRequest request = new OperationRequest();
        request.setOperation("ADD");
        request.getValues().add(1.0);
        request.getValues().add(2.0);

        Mockito.when(calculatorService.calculate(Mockito.any(), Mockito.anyList())).thenThrow(new IllegalArgumentException("Invalid value"));

        mockMvc.perform(post("/operation").content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Validation Error")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", hasSize(1)));
    }

    @Test
    void exceptionHandlerInvalidOperationTest() throws Exception {

        OperationRequest request = new OperationRequest();
        request.setOperation("");
        request.getValues().add(1.0);
        request.getValues().add(2.0);

        mockMvc.perform(post("/operation").content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Validation Error")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", contains("Operation type is required")));
    }

    @Test
    void exceptionHandlerInvalidValuesTest() throws Exception {

        OperationRequest request = new OperationRequest();
        request.setOperation("ADD");
        request.getValues().add(1.0);

        mockMvc.perform(post("/operation").content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Validation Error")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", contains("Value must have at least 2 values are required.")));

    }

    @Test
    void operationAddTest() throws Exception {

        OperationRequest request = new OperationRequest();
        request.setOperation("ADD");
        request.getValues().add(1.0);
        request.getValues().add(2.0);

        Mockito.when(calculatorService.calculate(Mockito.eq(OperationType.ADD), Mockito.eq(Arrays.asList(1.0, 2.0)))).thenReturn(3.0);

        mockMvc.perform(post("/operation").content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", is(3.0)));

    }

    @Test
    void operationSubTest() throws Exception {

        OperationRequest request = new OperationRequest();
        request.setOperation("SUB");
        request.getValues().add(1.0);
        request.getValues().add(2.0);

        Mockito.when(calculatorService.calculate(Mockito.eq(OperationType.SUB), Mockito.eq(Arrays.asList(1.0, 2.0)))).thenReturn(1.0);

        mockMvc.perform(post("/operation").content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", is(1.0)));

    }

    @Test
    void operationDivideTest() throws Exception {

        OperationRequest request = new OperationRequest();
        request.setOperation("DIV");
        request.getValues().add(4.0);
        request.getValues().add(2.0);

        Mockito.when(calculatorService.calculate(Mockito.eq(OperationType.DIV), Mockito.eq(Arrays.asList(4.0, 2.0)))).thenReturn(2.0);

        mockMvc.perform(post("/operation").content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", is(2.0)));

    }

    @Test
    void operationMultiplyTest() throws Exception {

        OperationRequest request = new OperationRequest();
        request.setOperation("MULT");
        request.getValues().add(4.0);
        request.getValues().add(2.0);

        Mockito.when(calculatorService.calculate(Mockito.eq(OperationType.MULT), Mockito.eq(Arrays.asList(4.0, 2.0)))).thenReturn(8.0);

        mockMvc.perform(post("/operation").content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", is(8.0)));

    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}