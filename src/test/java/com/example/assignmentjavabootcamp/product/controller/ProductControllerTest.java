package com.example.assignmentjavabootcamp.product.controller;

import com.example.assignmentjavabootcamp.product.model.Products;
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
import static org.mockito.ArgumentMatchers.anyInt;
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
        Products products = new Products();
        products.setName("product");
        when(productsRepository.findAllByNameContains(anyString())).thenReturn(Collections.singletonList(products));

        Response actual = testRestTemplate.getForObject("/product?name=product", Response.class);

        Map expected = objectMapper.convertValue(products, HashMap.class);
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

    @Test
    @DisplayName("test case get product by id and found.")
    void test_getProductById_success() {
        Products products = new Products();
        products.setId(1);
        products.setName("product");
        when(productsRepository.findById(anyInt())).thenReturn(Optional.of(products));

        Response actual = testRestTemplate.getForObject("/product/1", Response.class);

        Map expected = objectMapper.convertValue(products, HashMap.class);
        assertEquals(ResponseMessageEnum.SUCCESS.getMessage(), actual.getMessage());
        assertEquals(expected, actual.getData());
    }

    @Test
    @DisplayName("test case get product by id and not found.")
    void test_getProductById_notFound() {
        when(productsRepository.findById(anyInt())).thenReturn(Optional.empty());

        Response actual = testRestTemplate.getForObject("/product/1", Response.class);

        assertEquals("Product id : 1 not found.", actual.getMessage());
        assertEquals(new ArrayList<>(), actual.getData());
    }
}