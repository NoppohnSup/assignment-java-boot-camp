package com.example.assignmentjavabootcamp.product.controller;

import com.example.assignmentjavabootcamp.product.exception.ProductNotFoundException;
import com.example.assignmentjavabootcamp.product.model.ProductsEntity;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("/product")
    public Response searchProductByName(@RequestParam String name) {
        List<ProductsEntity> productsEntities = productsRepository.findAllByNameContains(name);
        if (CollectionUtils.isEmpty(productsEntities)) {
            throw new ProductNotFoundException(name);
        }
        return new Response(productsEntities, ResponseMessageEnum.SUCCESS.getMessage());
    }
}
