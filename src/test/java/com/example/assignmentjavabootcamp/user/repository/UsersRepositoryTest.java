package com.example.assignmentjavabootcamp.user.repository;

import com.example.assignmentjavabootcamp.user.model.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @DisplayName("repository test case find user by id and found.")
    void test_findById_success() {
        Users users = new Users();
        users.setId(1000);
        users.setFirstName("first");
        users.setLastName("last");
        usersRepository.save(users);

        Optional<Users> actual = usersRepository.findById(1000);

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("repository test case find user by id and not found.")
    void test_findById_fail_not_found() {
        Optional<Users> actual = usersRepository.findById(1000);

        assertFalse(actual.isPresent());
    }
}