package com.example.assignmentjavabootcamp.user.controller;

import com.example.assignmentjavabootcamp.user.exception.UserNotFoundException;
import com.example.assignmentjavabootcamp.user.model.Users;
import com.example.assignmentjavabootcamp.user.repository.ShippingAddressRepository;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @GetMapping("/user/{id}")
    public Response getUserById(@PathVariable Integer id){
        Optional<Users> usersEntity = usersRepository.findById(id);
        if (!usersEntity.isPresent()) {
            throw new UserNotFoundException(id);
        }
        return new Response(usersEntity, ResponseMessageEnum.SUCCESS.getMessage());
    }
}
