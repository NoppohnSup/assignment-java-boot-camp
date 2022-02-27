package com.example.assignmentjavabootcamp.order.controller;

import com.example.assignmentjavabootcamp.order.model.dto.BasketDTO;
import com.example.assignmentjavabootcamp.order.model.request.AddBasketRequest;
import com.example.assignmentjavabootcamp.order.service.BasketService;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasketControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BasketService basketService;

    @Test
    @DisplayName("test case add basket items.")
    void test_addBasketItems_success() {
        AddBasketRequest request = AddBasketRequest.builder()
                .qty(2)
                .productId(1)
                .userId(1)
                .build();

        BasketDTO basketDTO = BasketDTO.builder()
                .id(1)
                .totalPrice(100)
                .build();
        when(basketService.addBasketItems(any(AddBasketRequest.class))).thenReturn(basketDTO);
        Response actual = testRestTemplate.postForObject("/basket", request, Response.class);

        Map map = objectMapper.convertValue(basketDTO, HashMap.class);
        assertEquals(ResponseMessageEnum.SUCCESS.getMessage(), actual.getMessage());
        assertEquals(map, actual.getData());
    }
}