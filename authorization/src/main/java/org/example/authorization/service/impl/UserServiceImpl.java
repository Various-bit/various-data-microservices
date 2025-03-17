package org.example.authorization.service.impl;

import lombok.AllArgsConstructor;
import org.example.authorization.service.JWTService;
import org.example.authorization.dto.JWT;
import org.example.authorization.entity.User;
import org.example.authorization.repository.UserRepository;
import org.example.authorization.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;


    public void registerUser(User user) throws IOException, InterruptedException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUuid(UUID.randomUUID());
        addAuthority(user.getUuid());
        userRepository.save(user);
    }

    private void addAuthority(UUID uuid) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://subscribe-ms:8080/api/subscribe/signup"))
                .header("X-UUID", uuid.toString())
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public JWT loginUser(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername(), user.getUuid());
        }

        return null;
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUsername())
                .orElse(null);
    }
}
