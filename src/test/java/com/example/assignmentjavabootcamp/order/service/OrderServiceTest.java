package com.example.assignmentjavabootcamp.order.service;

import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.order.model.OrderItems;
import com.example.assignmentjavabootcamp.order.model.Orders;
import com.example.assignmentjavabootcamp.order.model.dto.BasketDTO;
import com.example.assignmentjavabootcamp.order.model.dto.OrderDTO;
import com.example.assignmentjavabootcamp.order.model.dto.OrderItemDTO;
import com.example.assignmentjavabootcamp.order.model.request.AddBasketRequest;
import com.example.assignmentjavabootcamp.order.model.request.ConfirmOrderRequest;
import com.example.assignmentjavabootcamp.order.repository.BasketItemsRepository;
import com.example.assignmentjavabootcamp.order.repository.BasketRepository;
import com.example.assignmentjavabootcamp.order.repository.OrderRepository;
import com.example.assignmentjavabootcamp.product.model.Products;
import com.example.assignmentjavabootcamp.product.model.dto.ProductDTO;
import com.example.assignmentjavabootcamp.user.model.ShippingAddress;
import com.example.assignmentjavabootcamp.user.model.Users;
import com.example.assignmentjavabootcamp.user.model.dto.ShippingAddressDTO;
import com.example.assignmentjavabootcamp.user.model.dto.UsersDTO;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import com.example.assignmentjavabootcamp.utils.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;

    @Mock
    UsersRepository usersRepository;

    @Mock
    BasketRepository basketRepository;

    @Mock
    BasketItemsRepository basketItemsRepository;

    @Mock
    DateUtils dateUtils;

    @Test
    @DisplayName("test case confirm order success.")
    void test_confirmOrder_success() {
        OrderService orderService = new OrderService();
        orderService.setBasketRepository(basketRepository);
        orderService.setOrderRepository(orderRepository);
        orderService.setBasketItemsRepository(basketItemsRepository);
        orderService.setUsersRepository(usersRepository);
        orderService.setDateUtils(dateUtils);

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1);
        shippingAddress.setAddress("address");

        Users users = new Users();
        users.setId(1);
        users.setFirstName("first");
        users.setLastName("last");
        users.setShippingAddress(Collections.singletonList(shippingAddress));

        Products products = new Products();
        products.setId(1);
        products.setName("product1");
        products.setTotalPrice(1000);
        products.setPercentSalePrice(10);

        OrderItems orderItems = new OrderItems();
        orderItems.setId(1);
        orderItems.setProducts(products);

        Orders orders = new Orders();
        orders.setId(1);
        orders.setTotalPrice(1800);
        orders.setUsers(users);

        Basket basket = new Basket();
        basket.setId(1);

        when(usersRepository.findById(anyInt())).thenReturn(Optional.of(users));
        when(basketRepository.findById((anyInt()))).thenReturn(Optional.of(basket));
        when(orderRepository.save(any(Orders.class))).thenReturn(orders);
        when(dateUtils.getCurrentDate()).thenReturn(new Timestamp(1645950039));

        ConfirmOrderRequest request = ConfirmOrderRequest.builder()
                .basketId(1)
                .userId(1)
                .build();
        OrderDTO actual = orderService.confirmOrder(request);

        verify(usersRepository).findById(1);
        verify(basketRepository).findById(1);

        OrderDTO expected = OrderDTO.builder()
                .id(1)
                .totalPrice(1800)
                .user(getUsersDTO(orders))
                .orderItems(getOrderItemDTOS(orders))
                .build();

        assertEquals(expected, actual);
    }

    private UsersDTO getUsersDTO(Orders saveOrder) {
        Users users = saveOrder.getUsers();
        List<ShippingAddress> shippingAddress = users.getShippingAddress();
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

    private List<OrderItemDTO> getOrderItemDTOS(Orders saveOrder) {
        return saveOrder.getOrderItems().stream()
                .map(item -> {
                            Products products = item.getProducts();
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
                            return OrderItemDTO.builder()
                                    .id(item.getId())
                                    .qty(item.getQty())
                                    .createdAt(item.getCreatedAt())
                                    .updatedAt(item.getUpdatedAt())
                                    .product(productDTO)
                                    .build();
                        }
                ).collect(Collectors.toList());
    }

}