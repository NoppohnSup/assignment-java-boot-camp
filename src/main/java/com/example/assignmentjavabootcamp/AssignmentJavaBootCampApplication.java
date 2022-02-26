package com.example.assignmentjavabootcamp;

import com.example.assignmentjavabootcamp.user.model.UsersEntity;
import com.example.assignmentjavabootcamp.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AssignmentJavaBootCampApplication {
	@Autowired
	UsersRepository usersRepository;

	@PostConstruct
	public void initData(){
		UsersEntity usersEntity = new UsersEntity();
		usersEntity.setId(1);
		usersEntity.setFirstName("first");
		usersEntity.setLastName("last");
		usersRepository.save(usersEntity);
	}

	public static void main(String[] args) {
		SpringApplication.run(AssignmentJavaBootCampApplication.class, args);
	}

}
