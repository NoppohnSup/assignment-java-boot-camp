package com.example.assignmentjavabootcamp.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class UsersEntity {
    @Id
    private int id;
    private String firstName;
    private String lastName;
}
