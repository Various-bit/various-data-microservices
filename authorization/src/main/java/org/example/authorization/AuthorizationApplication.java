package org.example.authorization;

import lombok.AllArgsConstructor;
import org.example.authorization.service.UserService;
import org.example.authorization.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}

}
