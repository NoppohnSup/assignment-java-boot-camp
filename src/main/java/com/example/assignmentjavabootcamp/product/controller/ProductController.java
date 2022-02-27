package com.example.assignmentjavabootcamp.product.controller;

import com.example.assignmentjavabootcamp.product.exception.ProductNotFoundException;
import com.example.assignmentjavabootcamp.product.model.Products;
import com.example.assignmentjavabootcamp.product.repository.ProductsRepository;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("/product")
    public Response searchProductByName(@RequestParam String name) {
        List<Products> productsEntities = productsRepository.findAllByNameContains(name);
        if (CollectionUtils.isEmpty(productsEntities)) {
            throw new ProductNotFoundException(String.format("name : %s", name));
        }
        return new Response(productsEntities, ResponseMessageEnum.SUCCESS.getMessage());
    }

    @GetMapping("/product/{id}")
    public Response getProductById(@PathVariable Integer id) {
        Optional<Products> productsEntity = productsRepository.findById(id);
        if (!productsEntity.isPresent()) {
            throw new ProductNotFoundException(String.format("id : %s", id));
        }
        return new Response(productsEntity, ResponseMessageEnum.SUCCESS.getMessage());
    }
}
