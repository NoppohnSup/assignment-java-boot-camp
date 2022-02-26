package com.example.assignmentjavabootcamp.user.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id) {
        super(String.valueOf(id));
    }
}
