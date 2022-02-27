package com.example.assignmentjavabootcamp.order.service;

import com.example.assignmentjavabootcamp.order.exception.BasketCannotAddException;
import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.order.model.BasketItems;
import com.example.assignmentjavabootcamp.order.model.dto.BasketDTO;
import com.example.assignmentjavabootcamp.order.model.dto.BasketItemDTO;
import com.example.assignmentjavabootcamp.order.model.request.AddBasketRequest;
import com.example.assignmentjavabootcamp.order.repository.BasketItemsRepository;
import com.example.assignmentjavabootcamp.order.repository.BasketRepository;
import com.example.assignmentjavabootcamp.product.model.ProductsEntity;
import com.example.assignmentjavabootcamp.product.model.dto.ProductDTO;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import com.example.assignmentjavabootcamp.user.model.ShippingAddressEntity;
import com.example.assignmentjavabootcamp.user.model.UsersEntity;
import com.example.assignmentjavabootcamp.user.model.dto.ShippingAddressDTO;
import com.example.assignmentjavabootcamp.user.model.dto.UsersDTO;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import com.example.assignmentjavabootcamp.utils.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {
    @Mock
    BasketRepository basketRepository;

    @Mock
    BasketItemsRepository basketItemsRepository;

    @Mock
    UsersRepository usersRepository;

    @Mock
    ProductsRepository productsRepository;

    @Mock
    DateUtils dateUtils;

    @Test
    @DisplayName("test case add new basket and basket items.")
    void test_addBasketItems_success() {
        BasketService basketService = new BasketService();
        basketService.setBasketRepository(basketRepository);
        basketService.setBasketItemsRepository(basketItemsRepository);
        basketService.setProductsRepository(productsRepository);
        basketService.setUsersRepository(usersRepository);
        basketService.setDateUtils(dateUtils);

        ShippingAddressEntity shippingAddressEntity = new ShippingAddressEntity();
        shippingAddressEntity.setId(1);
        shippingAddressEntity.setAddress("address");

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(1);
        usersEntity.setFirstName("first");
        usersEntity.setLastName("last");
        usersEntity.setShippingAddress(Collections.singletonList(shippingAddressEntity));

        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setId(1);
        productsEntity.setName("product1");
        productsEntity.setTotalPrice(1000);
        productsEntity.setPercentSalePrice(10);

        Basket basket = new Basket();
        basket.setId(1);
        basket.setTotalPrice(1800);
        basket.setUsers(usersEntity);

        when(usersRepository.findById(anyInt())).thenReturn(Optional.of(usersEntity));
        when(productsRepository.findById(anyInt())).thenReturn(Optional.of(productsEntity));
        when(basketRepository.findByUsersId((anyInt()))).thenReturn(Optional.empty());
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);
        when(dateUtils.getCurrentDate()).thenReturn(new Timestamp(1645950039));

        AddBasketRequest request = AddBasketRequest.builder()
                .qty(2)
                .productId(1)
                .userId(1)
                .build();
        BasketDTO actual = basketService.addBasketItems(request);

        verify(usersRepository).findById(1);
        verify(productsRepository).findById(1);
        verify(basketRepository).findByUsersId(usersEntity.getId());

        BasketDTO expected = BasketDTO.builder()
                .id(1)
                .totalPrice(1800)
                .user(getUsersDTO(basket))
                .basketItems(new ArrayList<>())
                .build();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test case add already basket and basket items.")
    void test_addBasketItems_already_basket_success() {
        BasketService basketService = new BasketService();
        basketService.setBasketRepository(basketRepository);
        basketService.setBasketItemsRepository(basketItemsRepository);
        basketService.setProductsRepository(productsRepository);
        basketService.setUsersRepository(usersRepository);
        basketService.setDateUtils(dateUtils);

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(1);
        usersEntity.setFirstName("first");
        usersEntity.setLastName("last");

        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setId(1);
        productsEntity.setName("product1");
        productsEntity.setTotalPrice(1000);
        productsEntity.setPercentSalePrice(10);

        BasketItems basketItems = new BasketItems();
        basketItems.setId(1);
        basketItems.setQty(3);
        basketItems.setProducts(productsEntity);

        Basket basket = new Basket();
        basket.setId(1);
        basket.setTotalPrice(1800);
        basket.setUsers(usersEntity);
        basket.addToBasketItems(basketItems);

        when(usersRepository.findById(anyInt())).thenReturn(Optional.of(usersEntity));
        when(productsRepository.findById(anyInt())).thenReturn(Optional.of(productsEntity));
        when(basketRepository.findByUsersId(anyInt())).thenReturn(Optional.of(basket));
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);
        when(dateUtils.getCurrentDate()).thenReturn(new Timestamp(1645950039));

        AddBasketRequest request = AddBasketRequest.builder()
                .qty(2)
                .productId(1)
                .userId(1)
                .build();
        BasketDTO actual = basketService.addBasketItems(request);

        verify(usersRepository).findById(1);
        verify(productsRepository).findById(1);
        verify(basketRepository).findByUsersId(usersEntity.getId());

        BasketDTO expected = BasketDTO.builder()
                .id(1)
                .totalPrice(3600)
                .updatedAt(new Timestamp(1645950039))
                .user(getUsersDTO(basket))
                .basketItems(getBasketItemDTOS(basket))
                .build();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test case add already basket and product in basket items.")
    void test_addBasketItems_already_basket_and_product_success() {
        BasketService basketService = new BasketService();
        basketService.setBasketRepository(basketRepository);
        basketService.setBasketItemsRepository(basketItemsRepository);
        basketService.setProductsRepository(productsRepository);
        basketService.setUsersRepository(usersRepository);
        basketService.setDateUtils(dateUtils);

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(1);
        usersEntity.setFirstName("first");
        usersEntity.setLastName("last");

        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setId(1);
        productsEntity.setName("product1");
        productsEntity.setTotalPrice(1000);
        productsEntity.setPercentSalePrice(10);

        BasketItems basketItems = new BasketItems();
        basketItems.setId(1);
        basketItems.setQty(3);
        basketItems.setProducts(productsEntity);

        Basket basket = new Basket();
        basket.setId(1);
        basket.setTotalPrice(1800);
        basket.setUsers(usersEntity);
        basket.addToBasketItems(basketItems);

        when(usersRepository.findById(anyInt())).thenReturn(Optional.of(usersEntity));
        when(productsRepository.findById(anyInt())).thenReturn(Optional.of(productsEntity));
        when(basketRepository.findByUsersId(anyInt())).thenReturn(Optional.of(basket));
        when(basketItemsRepository.findByProductsAndBasket(any(ProductsEntity.class), any(Basket.class))).thenReturn(Optional.of(basketItems));
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);
        when(dateUtils.getCurrentDate()).thenReturn(new Timestamp(1645950039));

        AddBasketRequest request = AddBasketRequest.builder()
                .qty(2)
                .productId(1)
                .userId(1)
                .build();
        BasketDTO actual = basketService.addBasketItems(request);

        verify(usersRepository).findById(1);
        verify(productsRepository).findById(1);
        verify(basketRepository).findByUsersId(usersEntity.getId());

        BasketDTO expected = BasketDTO.builder()
                .id(1)
                .totalPrice(900)
                .updatedAt(new Timestamp(1645950039))
                .user(getUsersDTO(basket))
                .basketItems(getBasketItemDTOS(basket))
                .build();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test case cannot add basket.")
    void test_addBasketItems_cannot_add_basket() {
        BasketService basketService = new BasketService();
        basketService.setProductsRepository(productsRepository);
        basketService.setUsersRepository(usersRepository);

        when(usersRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(productsRepository.findById(anyInt())).thenReturn(Optional.empty());

        AddBasketRequest request = AddBasketRequest.builder()
                .qty(2)
                .productId(1)
                .userId(1)
                .build();
        BasketCannotAddException exception = assertThrows(BasketCannotAddException.class, () -> basketService.addBasketItems(request));

        String expectedMessage = "Cannot add basket.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private UsersDTO getUsersDTO(Basket saveBasket) {
        UsersEntity users = saveBasket.getUsers();
        List<ShippingAddressEntity> shippingAddress = users.getShippingAddress();
        List<ShippingAddressDTO> shippingAddressDTOS = shippingAddress.stream()
                .map(item -> ShippingAddressDTO.builder()
                        .id(item.getId())
                        .address(item.getAddress())
                        .district(item.getDistrict())
                        .postcode(item.getPostcode())
                        .province(item.getProvince())
                        .build())
                .collect(Collectors.toList());
        return UsersDTO.builder()
                .id(users.getId())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .shippingAddress(shippingAddressDTOS)
                .build();
    }

    private List<BasketItemDTO> getBasketItemDTOS(Basket saveBasket) {
        return saveBasket.getBasketItems().stream()
                .map(item -> {
                            ProductsEntity products = item.getProducts();
                            ProductDTO productDTO = ProductDTO.builder()
                                    .id(products.getId())
                                    .amount(products.getAmount())
                                    .brand(products.getBrand())
                                    .detail(products.getDetail())
                                    .name(products.getName())
                                    .sizeProducts(products.getSizeProducts())
                                    .totalPrice(products.getTotalPrice())
                                    .percentSalePrice(products.getPercentSalePrice())
                                    .pictureProducts(products.getPictureProducts())
                                    .saleEndDate(products.getSaleEndDate())
                                    .build();
                            return BasketItemDTO.builder()
                                    .id(item.getId())
                                    .qty(item.getQty())
                                    .createdAt(item.getCreatedAt())
                                    .updatedAt(item.getUpdatedAt())
                                    .productDTO(productDTO)
                                    .build();
                        }
                ).collect(Collectors.toList());
    }
}