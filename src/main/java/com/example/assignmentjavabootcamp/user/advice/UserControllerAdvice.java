package com.example.assignmentjavabootcamp.user.advice;

import com.example.assignmentjavabootcamp.user.exception.UserNotFoundException;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNotFound(UserNotFoundException e){
        return new Response(new ArrayList<>(), String.format("User id : %s not found.", e.getMessage()));
    }
}
