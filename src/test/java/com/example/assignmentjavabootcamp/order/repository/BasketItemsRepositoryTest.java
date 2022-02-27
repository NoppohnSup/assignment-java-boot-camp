package com.example.assignmentjavabootcamp.order.repository;

import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.order.model.BasketItems;
import com.example.assignmentjavabootcamp.product.model.Products;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BasketItemsRepositoryTest {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketItemsRepository basketItemsRepository;

    @Test
    @DisplayName("repository test case find itemBasket with products and basket and found.")
    void test_findByProductsAndBasket_success() {
        Basket basket = new Basket();
        basket.setTotalPrice(2000);

        Products products = new Products();
        productsRepository.save(products);

        BasketItems basketItems = new BasketItems();
        basketItems.setBasket(basket);
        basketItems.setProducts(products);

        basket.addToBasketItems(basketItems);
        basketRepository.save(basket);

        Optional<BasketItems> actual = basketItemsRepository.findByProductsAndBasket(products, basket);
        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("repository test case find itemBasket with products and basket and not found.")
    void test_findByProductsAndBasket_fail() {
        Basket basket = new Basket();
        basket.setId(1000);
        Products products = new Products();
        products.setId(1000);

        Optional<BasketItems> actual = basketItemsRepository.findByProductsAndBasket(products, basket);
        assertFalse(actual.isPresent());
    }
}