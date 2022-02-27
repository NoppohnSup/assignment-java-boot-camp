package com.example.assignmentjavabootcamp.order.service;

import com.example.assignmentjavabootcamp.order.exception.ConfirmOrderException;
import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.order.model.BasketItems;
import com.example.assignmentjavabootcamp.order.model.Orders;
import com.example.assignmentjavabootcamp.order.model.OrderItems;
import com.example.assignmentjavabootcamp.order.model.dto.BasketItemDTO;
import com.example.assignmentjavabootcamp.order.model.dto.OrderDTO;
import com.example.assignmentjavabootcamp.order.model.dto.OrderItemDTO;
import com.example.assignmentjavabootcamp.order.model.request.ConfirmOrderRequest;
import com.example.assignmentjavabootcamp.order.repository.BasketItemsRepository;
import com.example.assignmentjavabootcamp.order.repository.BasketRepository;
import com.example.assignmentjavabootcamp.order.repository.OrderItemsRepository;
import com.example.assignmentjavabootcamp.order.repository.OrderRepository;
import com.example.assignmentjavabootcamp.product.model.Products;
import com.example.assignmentjavabootcamp.product.model.dto.ProductDTO;
import com.example.assignmentjavabootcamp.user.model.ShippingAddress;
import com.example.assignmentjavabootcamp.user.model.Users;
import com.example.assignmentjavabootcamp.user.model.dto.ShippingAddressDTO;
import com.example.assignmentjavabootcamp.user.model.dto.UsersDTO;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import com.example.assignmentjavabootcamp.utils.DateUtils;
import lombok.Setter;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    @Setter
    OrderRepository orderRepository;

    @Autowired
    @Setter
    UsersRepository usersRepository;

    @Autowired
    @Setter
    BasketRepository basketRepository;

    @Autowired
    @Setter
    BasketItemsRepository basketItemsRepository;

    @Autowired
    @Setter
    DateUtils dateUtils;

    @Transactional
    public OrderDTO confirmOrder(ConfirmOrderRequest confirmOrderRequest){
        Optional<Users> user = usersRepository.findById(confirmOrderRequest.getUserId());
        Optional<Basket> basket = basketRepository.findById(confirmOrderRequest.getBasketId());

        if (user.isPresent() && basket.isPresent()){
            Basket basketData = basket.get();
            Users userData = user.get();

            Orders orders = new Orders();
            orders.setUsers(userData);
            orders.setTotalPrice(basketData.getTotalPrice());
            orders.setCreatedAt(dateUtils.getCurrentDate());
            orders.setUpdatedAt(dateUtils.getCurrentDate());

            for (BasketItems item : basketData.getBasketItems()) {
                OrderItems orderItems = new OrderItems();
                orderItems.setQty(item.getQty());
                orderItems.setProducts(item.getProducts());
                orderItems.setCreatedAt(dateUtils.getCurrentDate());
                orderItems.setUpdatedAt(dateUtils.getCurrentDate());
                orders.addToOrderItems(orderItems);
            }

            Orders saveOrders = orderRepository.save(orders);

            UsersDTO usersDTO = getUsersDTO(saveOrders);
            List<OrderItemDTO> orderItemDTOS = getOrderItemDTOS(saveOrders);

            return OrderDTO.builder()
                    .id(saveOrders.getId())
                    .user(usersDTO)
                    .totalPrice(saveOrders.getTotalPrice())
                    .createdAt(saveOrders.getCreatedAt())
                    .updatedAt(saveOrders.getUpdatedAt())
                    .orderItems(orderItemDTOS)
                    .build();
        }

        throw new ConfirmOrderException("Basket not found.");
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
