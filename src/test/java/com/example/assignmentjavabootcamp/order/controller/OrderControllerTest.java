package com.example.assignmentjavabootcamp.order.controller;

import com.example.assignmentjavabootcamp.order.model.dto.BasketDTO;
import com.example.assignmentjavabootcamp.order.model.dto.OrderDTO;
import com.example.assignmentjavabootcamp.order.model.request.AddBasketRequest;
import com.example.assignmentjavabootcamp.order.model.request.ConfirmOrderRequest;
import com.example.assignmentjavabootcamp.order.service.BasketService;
import com.example.assignmentjavabootcamp.order.service.OrderService;
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
class OrderControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("test case confirm order.")
    void test_confirmOrder_success() {
        ConfirmOrderRequest request = ConfirmOrderRequest.builder()
                .basketId(1)
                .userId(1)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1)
                .totalPrice(100)
                .build();
        when(orderService.confirmOrder(any(ConfirmOrderRequest.class))).thenReturn(orderDTO);
        Response actual = testRestTemplate.postForObject("/confirm/order", request, Response.class);

        Map map = objectMapper.convertValue(orderDTO, HashMap.class);
        assertEquals(ResponseMessageEnum.SUCCESS.getMessage(), actual.getMessage());
        assertEquals(map, actual.getData());
    }
}