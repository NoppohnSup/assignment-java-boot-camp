package com.example.assignmentjavabootcamp.order.repository;

import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.user.model.UsersEntity;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BasketRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Test
    @DisplayName("repository test case find basket by userId and found.")
    void test_findByUsers_success() {
        UsersEntity users = new UsersEntity();
        users.setId(1000);
        UsersEntity save = usersRepository.save(users);

        Basket basket = new Basket();
        basket.addUsers(save);
        basketRepository.save(basket);

        Optional<Basket> actual = basketRepository.findByUsersId(1000);
        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("repository test case find basket by userId and not found.")
    void test_findByUsers_fail() {
        Optional<Basket> actual = basketRepository.findByUsersId(1000);
        assertFalse(actual.isPresent());
    }

}