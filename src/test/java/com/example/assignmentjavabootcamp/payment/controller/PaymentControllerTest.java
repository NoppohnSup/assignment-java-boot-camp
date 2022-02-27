package com.example.assignmentjavabootcamp.payment.controller;

import com.example.assignmentjavabootcamp.payment.entity.PaymentChannel;
import com.example.assignmentjavabootcamp.payment.repository.PaymentChannelRepository;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentChannelRepository paymentChannelRepository;

    @Test
    @DisplayName("test case get all payment channel and found.")
    void test_getAllPaymentChannel_success() {
        PaymentChannel paymentChannel = new PaymentChannel();
        paymentChannel.setName("Credit");
        when(paymentChannelRepository.findAll()).thenReturn(Collections.singletonList(paymentChannel));

        Response actual = testRestTemplate.getForObject("/payment_channel", Response.class);

        Map expected = objectMapper.convertValue(paymentChannel, HashMap.class);
        assertEquals(ResponseMessageEnum.SUCCESS.getMessage(), actual.getMessage());
        assertEquals(Collections.singletonList(expected), actual.getData());
    }

    @Test
    @DisplayName("test case get all payment channel and not found.")
    void test_getAllPaymentChannel_not_found() {
        when(paymentChannelRepository.findAll()).thenReturn(new ArrayList<>());

        Response actual = testRestTemplate.getForObject("/payment_channel", Response.class);

        assertEquals("Payment channel not found.", actual.getMessage());
        assertEquals(new ArrayList<>(), actual.getData());
    }
}