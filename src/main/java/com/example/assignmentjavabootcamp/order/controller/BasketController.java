package com.example.assignmentjavabootcamp.order.controller;

import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.order.model.dto.BasketDTO;
import com.example.assignmentjavabootcamp.order.model.request.AddBasketRequest;
import com.example.assignmentjavabootcamp.order.repository.BasketRepository;
import com.example.assignmentjavabootcamp.order.service.BasketService;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BasketController {
    @Autowired
    BasketService basketService;

    @PostMapping(value = "/basket", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addBasketItems(@RequestBody AddBasketRequest request){
        BasketDTO basket = basketService.addBasketItems(request);
        return new Response(basket, ResponseMessageEnum.SUCCESS.getMessage());
    }

    @GetMapping(value = "/basket/{id}")
    public Response getBasketItems(@PathVariable Integer id){
        BasketDTO basket = basketService.findBasketDetailsById(id);
        return new Response(basket, ResponseMessageEnum.SUCCESS.getMessage());
    }
}
