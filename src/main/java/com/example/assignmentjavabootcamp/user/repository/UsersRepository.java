package com.example.assignmentjavabootcamp.user.repository;

import com.example.assignmentjavabootcamp.user.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
}
