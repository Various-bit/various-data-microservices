package org.example.authorization.service;

import org.example.authorization.dto.JWT;
import org.example.authorization.dto.RefreshToken;
import org.example.authorization.dto.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public interface JWTService {

    public JWT generateToken(String subject, UUID uuid);

    public String extractUsername(String token);

    public UUID extractUUID(String token);

    public boolean validateToken(String token, UserPrincipal userDetails);

    String generateAccessToken(String user, UUID uuid, RefreshToken refreshToken);
}
