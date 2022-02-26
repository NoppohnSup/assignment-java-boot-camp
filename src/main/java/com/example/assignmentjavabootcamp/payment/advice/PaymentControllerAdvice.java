package com.example.assignmentjavabootcamp.payment.advice;

import com.example.assignmentjavabootcamp.payment.exception.PaymentChannelNotFoundException;
import com.example.assignmentjavabootcamp.product.exception.ProductNotFoundException;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class PaymentControllerAdvice {
    @ExceptionHandler(PaymentChannelNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response paymentChannelNotFound(PaymentChannelNotFoundException e){
        return new Response(new ArrayList<>(), "Payment channel not found.");
    }
}
