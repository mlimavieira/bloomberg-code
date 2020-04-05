package com.bloomberg.client.controller;

import com.bloomberg.client.exception.ServiceIntegrationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Test;
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

import javax.validation.ValidationException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorController calculatorController;

    @Test
    public void handleRuntimeException() throws Exception {

        Mockito.when(calculatorController.sum(1.0, 2.0)).thenThrow(new RuntimeException("Unexpected Exception1"));

        mockMvc.perform(post("/calculator/add").content(asJsonString(Arrays.asList(1.0, 2.0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("Unexpected exception")))
                .andReturn();

    }

    @Test
    public void handleValidationException() throws Exception {

        Mockito.when(calculatorController.sum(any())).thenThrow(new ValidationException("Error during validation"));

        mockMvc.perform(post("/calculator/add").content(asJsonString(new ArrayList<>()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("Error during validation")))
                .andReturn();
    }

    @Test
    public void handleServiceIntegration() throws Exception {

        Mockito.when(calculatorController.sum(any())).thenThrow(new ServiceIntegrationException(HttpStatus.INTERNAL_SERVER_ERROR, "ServiceIntegrationException"));

        mockMvc.perform(post("/calculator/add").content(asJsonString(new ArrayList<>()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("ServiceIntegrationException")))
                .andReturn();
    }

    @Test
    public void handleConnectException() throws Exception {

        given(calculatorController.sum(any())).willAnswer(invocation -> { throw new ConnectException("ConnectException");});

        mockMvc.perform(post("/calculator/add").content(asJsonString(new ArrayList<>()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("Fail to connect to Calculator Core")))
                .andReturn();
    }

    @Test
    public void handleTimeoutException() throws Exception {

        given(calculatorController.sum(any())).willAnswer(invocation -> { throw new TimeoutException("TimeoutException");});

        mockMvc.perform(post("/calculator/add").content(asJsonString(new ArrayList<>()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("Fail to connect to Calculator Core")))
                .andReturn();
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