package com.example.assignmentjavabootcamp.product.advice;

import com.example.assignmentjavabootcamp.product.exception.ProductNotFoundException;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class ProductControllerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response productNotFound(ProductNotFoundException e){
        return new Response(new ArrayList<>(), String.format("Product name : %s not found.", e.getMessage()));
    }
}
