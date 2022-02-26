package com.example.assignmentjavabootcamp;

import com.example.assignmentjavabootcamp.product.model.ProductsEntity;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import com.example.assignmentjavabootcamp.user.model.UsersEntity;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class AssignmentJavaBootCampApplication {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ProductsRepository productsRepository;

    @PostConstruct
    public void initData() {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(1);
        usersEntity.setFirstName("first");
        usersEntity.setLastName("last");
        usersRepository.save(usersEntity);

        ProductsEntity productsEntity1 = new ProductsEntity();
        productsEntity1.setName("product 1");
        productsEntity1.setBrand("brand 1");
        productsEntity1.setDetail("detail 1");
        productsEntity1.setTotalPrice(1000);
        productsEntity1.setAmount(10);
        productsEntity1.setPercentSalePrice(20);
        productsEntity1.setSizeProducts(Arrays.asList("size 1", "size 2"));
        productsEntity1.setPictureProducts(Arrays.asList("image1.png", "image2.png"));
        productsEntity1.setSaleEndDate(getCurrentDate());

        ProductsEntity productsEntity2 = new ProductsEntity();
        productsEntity2.setName("product 2");
        productsEntity2.setBrand("brand 2");
        productsEntity2.setDetail("detail 2");
        productsEntity2.setTotalPrice(2000);
        productsEntity2.setAmount(100);
        productsEntity2.setPercentSalePrice(30);
        productsEntity2.setSizeProducts(Collections.singletonList("size 1"));
        productsEntity2.setPictureProducts(Arrays.asList("image1.png", "image2.png"));
        productsEntity2.setSaleEndDate(getCurrentDate());

        productsRepository.saveAll(Arrays.asList(productsEntity1, productsEntity2));
    }

    private Timestamp getCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        SpringApplication.run(AssignmentJavaBootCampApplication.class, args);
    }

}
