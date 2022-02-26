package com.example.assignmentjavabootcamp.product.controller;

import com.example.assignmentjavabootcamp.product.model.ProductsEntity;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("test case search product and found.")
    void test_searchProductByName_success() {
        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setName("product");
        when(productsRepository.findAllByNameContains(anyString())).thenReturn(Collections.singletonList(productsEntity));

        Response actual = testRestTemplate.getForObject("/product?name=product", Response.class);

        Map expected = objectMapper.convertValue(productsEntity, HashMap.class);
        assertEquals(ResponseMessageEnum.SUCCESS.getMessage(), actual.getMessage());
        assertEquals(Collections.singletonList(expected), actual.getData());
    }

    @Test
    @DisplayName("test case search product and not found.")
    void test_searchProductByName_notFound() {
        when(productsRepository.findAllByNameContains(anyString())).thenReturn(new ArrayList<>());

        Response actual = testRestTemplate.getForObject("/product?name=product", Response.class);

        assertEquals("Product name : product not found.", actual.getMessage());
        assertEquals(new ArrayList<>(), actual.getData());
    }
}