package com.example.assignmentjavabootcamp.order.advice;

import com.example.assignmentjavabootcamp.order.exception.BasketCannotAddException;
import com.example.assignmentjavabootcamp.order.exception.BasketNotFoundException;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class BasketControllerAdvice {
    @ExceptionHandler(BasketCannotAddException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response basketCannotAdd(BasketCannotAddException e){
        return new Response(new ArrayList<>(), e.getMessage());
    }

    @ExceptionHandler(BasketNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response basketNotFound(BasketCannotAddException e){
        return new Response(new ArrayList<>(), e.getMessage());
    }
}
