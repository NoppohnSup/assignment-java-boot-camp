package com.example.assignmentjavabootcamp.product.repository;

import com.example.assignmentjavabootcamp.product.model.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("repository test case find product by name and found.")
    void test_findAllByNameContains_success() {
        Products products = new Products();
        products.setId(1000);
        products.setName("name");
        productsRepository.save(products);

        List<Products> actual = productsRepository.findAllByNameContains("name");

        assertFalse(CollectionUtils.isEmpty(actual));
    }

    @Test
    @DisplayName("repository test case find product by name and not found.")
    void test_findAllByNameContains_fail_not_found() {
        List<Products> actual = productsRepository.findAllByNameContains("name");

        assertTrue(CollectionUtils.isEmpty(actual));
    }

    @Test
    @DisplayName("repository test case find product by id and found.")
    void test_findById_success() {
        Products products = new Products();
        products.setId(1000);
        products.setName("name");
        productsRepository.save(products);

        Optional<Products> actual = productsRepository.findById(1000);

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("repository test case find product by id and not found.")
    void test_findById_fail_not_found() {
        Optional<Products> actual = productsRepository.findById(1000);

        assertFalse(actual.isPresent());
    }
}