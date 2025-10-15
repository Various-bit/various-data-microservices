package org.example.authorization.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.authorization.dto.JWT;
import org.example.authorization.dto.LoginRequest;
import org.example.authorization.dto.RefreshToken;
import org.example.authorization.entity.User;
import org.example.authorization.repository.UserRepository;
import org.example.authorization.service.JWTService;
import org.example.authorization.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping(path="/signup")
    public ResponseEntity<?> signup(@RequestBody User user) throws IOException, InterruptedException {
        if (userRepository.existsByEmail(user.getEmail()) ||
                userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.ok().build();
        }
        userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path="/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmailOrUsername(loginRequest.getUsername(),
                loginRequest.getUsername()).orElseThrow(null);

        if (user != null) {
            user.setPassword(loginRequest.getPassword());
            return ResponseEntity.ok().body(userService.loginUser(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @RequestMapping("/check")
    public ResponseEntity<?> check() {
        System.out.println("request get");
        String username = userService.getCurrentUser().getUsername();
        return ResponseEntity.ok().body(username);
    }

    @PostMapping(path="/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshToken refreshToken) {
        String user = jwtService.extractUsername(refreshToken.getToken()).describeConstable().orElseThrow(
                () -> new RuntimeException("Refresh token expired")
        );
        UUID uuid = jwtService.extractUUID(refreshToken.getToken());
        String token = jwtService.generateAccessToken(user, uuid, refreshToken);
        JWT jwt = new JWT();
        jwt.setAccessToken(token);
        return token != null ? ResponseEntity.ok().body(jwt) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
