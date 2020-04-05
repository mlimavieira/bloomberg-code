package com.bloomberg.client.controller;

import com.bloomberg.client.model.OperationResult;
import com.bloomberg.client.service.calculator.impl.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    public void sumTest() throws Exception {

        OperationResult operationResult = new OperationResult();
        operationResult.setTotal(3.0);
        operationResult.setNumbers(Arrays.asList(1.0, 2.0));

        Mockito.when(calculatorService.sum(1.0, 2.0)).thenReturn(operationResult);

        mockMvc.perform(post("/calculator/add").content(asJsonString(Arrays.asList(1.0, 2.0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", is(3.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers", hasItems(1.0, 2.0)));
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}