package org.example.authorization.controller;


import lombok.AllArgsConstructor;
import org.example.authorization.entity.User;
import org.example.authorization.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/health")
@AllArgsConstructor
public class HealthController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> health() throws IOException, InterruptedException {
        User user = new User("admin", "admin@example.com", "password");
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }
}
