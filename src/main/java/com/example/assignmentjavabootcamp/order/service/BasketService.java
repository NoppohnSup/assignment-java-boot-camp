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
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {
    @Autowired
    @Setter
    BasketRepository basketRepository;

    @Autowired
    @Setter
    BasketItemsRepository basketItemsRepository;

    @Autowired
    @Setter
    UsersRepository usersRepository;

    @Autowired
    @Setter
    ProductsRepository productsRepository;

    @Autowired
    @Setter
    DateUtils dateUtils;

    @Transactional
    public BasketDTO addBasketItems(AddBasketRequest request) {
        Optional<UsersEntity> userEntity = usersRepository.findById(request.getUserId());
        Optional<ProductsEntity> productsEntity = productsRepository.findById(request.getProductId());

        if (userEntity.isPresent() && productsEntity.isPresent()) {
            UsersEntity user = userEntity.get();
            ProductsEntity product = productsEntity.get();
            double productPrice = product.getTotalPrice() - (product.getTotalPrice() * (product.getPercentSalePrice() / 100));
            double totalPriceItem = productPrice * request.getQty();

            Optional<Basket> userBasket = basketRepository.findByUsersId(user.getId());

            Basket basket;
            if (userBasket.isPresent()) {
                basket = userBasket.get();
                double totalPriceUpdated = basket.getTotalPrice() + totalPriceItem;

                basket.setTotalPrice(totalPriceUpdated);
                basket.setUpdatedAt(dateUtils.getCurrentDate());

                Optional<BasketItems> basketItems = basketItemsRepository.findByProductsAndBasket(product, basket);
                BasketItems basketItem;
                if (basketItems.isPresent()) {
                    basketItem = basketItems.get();
                    double oldItemPrice = productPrice * basketItem.getQty();
                    basket.setTotalPrice(totalPriceUpdated - oldItemPrice);

                    basketItem.setQty(request.getQty());
                    basketItem.setUpdatedAt(dateUtils.getCurrentDate());
                } else {
                    basketItem = new BasketItems();
                    basketItem.setBasket(basket);
                    basketItem.setProducts(product);
                    basketItem.setQty(request.getQty());
                    basketItem.setCreatedAt(dateUtils.getCurrentDate());
                    basketItem.setUpdatedAt(dateUtils.getCurrentDate());
                    basket.addToBasketItems(basketItem);
                }
            } else {
                basket = new Basket();
                basket.setTotalPrice(totalPriceItem);
                basket.setCreatedAt(dateUtils.getCurrentDate());
                basket.setUpdatedAt(dateUtils.getCurrentDate());

                BasketItems basketItem = new BasketItems();
                basketItem.setQty(request.getQty());
                basketItem.setCreatedAt(dateUtils.getCurrentDate());
                basketItem.setUpdatedAt(dateUtils.getCurrentDate());
                basketItem.setBasket(basket);
                basketItem.setProducts(product);

                basket.addToBasketItems(basketItem);
                basket.addUsers(user);
            }
            Basket saveBasket = basketRepository.save(basket);

            List<BasketItemDTO> basketItemDTOS = getBasketItemDTOS(saveBasket);
            UsersDTO usersDTO = getUsersDTO(saveBasket);
            return BasketDTO.builder()
                    .id(saveBasket.getId())
                    .user(usersDTO)
                    .totalPrice(saveBasket.getTotalPrice())
                    .createdAt(saveBasket.getCreatedAt())
                    .updatedAt(saveBasket.getUpdatedAt())
                    .basketItems(basketItemDTOS)
                    .build();
        }

        throw new BasketCannotAddException("Cannot add basket.");
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
