package org.example.authorization.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String username;
    private String password;
    private String email;
}
