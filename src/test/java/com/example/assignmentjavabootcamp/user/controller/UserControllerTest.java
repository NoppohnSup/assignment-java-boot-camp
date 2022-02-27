package com.example.assignmentjavabootcamp.user.controller;

import com.example.assignmentjavabootcamp.user.model.Users;
import com.example.assignmentjavabootcamp.user.repository.ShippingAddressRepository;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private ShippingAddressRepository shippingAddressRepository;

    @Test
    @DisplayName("test case get user and found.")
    void test_getUserById_success() {
        Users users = new Users();
        users.setId(1000);
        users.setFirstName("first_name");
        users.setLastName("last_name");

        when(usersRepository.findById(anyInt())).thenReturn(Optional.of(users));

        Response actual = testRestTemplate.getForObject("/user/1", Response.class);

        Map expected = objectMapper.convertValue(users, HashMap.class);
        assertEquals(ResponseMessageEnum.SUCCESS.getMessage(), actual.getMessage());
        assertEquals(expected, actual.getData());
    }

    @Test
    @DisplayName("test case get user and not found.")
    void test_getUserById_not_found() {
        when(usersRepository.findById(anyInt())).thenReturn(Optional.empty());

        Response actual = testRestTemplate.getForObject("/user/1", Response.class);

        assertEquals("User id : 1 not found.", actual.getMessage());
        assertEquals(new ArrayList<>(), actual.getData());
    }
}