package com.example.assignmentjavabootcamp;

import com.example.assignmentjavabootcamp.payment.entity.PaymentChannel;
import com.example.assignmentjavabootcamp.payment.repository.PaymentChannelRepository;
import com.example.assignmentjavabootcamp.product.model.Products;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import com.example.assignmentjavabootcamp.user.model.ShippingAddress;
import com.example.assignmentjavabootcamp.user.model.Users;
import com.example.assignmentjavabootcamp.user.repository.ShippingAddressRepository;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class AssignmentJavaBootCampApplication {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @Autowired
    PaymentChannelRepository paymentChannelRepository;

    @PostConstruct
    public void initData() {
        Users users = new Users();
        users.setId(1);
        users.setFirstName("first");
        users.setLastName("last");
        usersRepository.save(users);

        Products products1 = new Products();
        products1.setId(1);
        products1.setName("product 1");
        products1.setBrand("brand 1");
        products1.setDetail("detail 1");
        products1.setTotalPrice(1000);
        products1.setAmount(10);
        products1.setPercentSalePrice(20);
        products1.setSizeProducts(Arrays.asList("size 1", "size 2"));
        products1.setPictureProducts(Arrays.asList("image1.png", "image2.png"));
        products1.setSaleEndDate(getCurrentDate());

        Products products2 = new Products();
        products2.setId(2);
        products2.setName("product 2");
        products2.setBrand("brand 2");
        products2.setDetail("detail 2");
        products2.setTotalPrice(2000);
        products2.setAmount(100);
        products2.setPercentSalePrice(30);
        products2.setSizeProducts(Collections.singletonList("size 1"));
        products2.setPictureProducts(Arrays.asList("image1.png", "image2.png"));
        products2.setSaleEndDate(getCurrentDate());

        productsRepository.saveAll(Arrays.asList(products1, products2));

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1);
        shippingAddress.setAddress("address");
        shippingAddress.setPostcode("11111");
        shippingAddress.setDistrict("district");
        shippingAddress.setProvince("province");
        shippingAddress.setUsers(users);

        ShippingAddress shippingAddress2 = new ShippingAddress();
        shippingAddress.setId(2);
        shippingAddress2.setAddress("address2");
        shippingAddress2.setPostcode("22222");
        shippingAddress2.setDistrict("district2");
        shippingAddress2.setProvince("province2");
        shippingAddress2.setUsers(users);
        shippingAddressRepository.saveAll(Arrays.asList(shippingAddress, shippingAddress2));

        PaymentChannel paymentChannel = new PaymentChannel();
        paymentChannel.setId(1);
        paymentChannel.setName("Credit");
        paymentChannelRepository.save(paymentChannel);
    }

    private Timestamp getCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        SpringApplication.run(AssignmentJavaBootCampApplication.class, args);
    }

}
