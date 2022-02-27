package com.example.assignmentjavabootcamp.order.repository;

import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.order.model.BasketItems;
import com.example.assignmentjavabootcamp.product.model.ProductsEntity;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

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

        ProductsEntity productsEntity = new ProductsEntity();
        productsRepository.save(productsEntity);

        BasketItems basketItems = new BasketItems();
        basketItems.setBasket(basket);
        basketItems.setProducts(productsEntity);

        basket.addToBasketItems(basketItems);
        basketRepository.save(basket);

        Optional<BasketItems> actual = basketItemsRepository.findByProductsAndBasket(productsEntity, basket);
        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("repository test case find itemBasket with products and basket and not found.")
    void test_findByProductsAndBasket_fail() {
        Basket basket = new Basket();
        basket.setId(1000);
        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setId(1000);

        Optional<BasketItems> actual = basketItemsRepository.findByProductsAndBasket(productsEntity, basket);
        assertFalse(actual.isPresent());
    }
}