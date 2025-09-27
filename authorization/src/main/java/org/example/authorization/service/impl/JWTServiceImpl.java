package org.example.authorization.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.authorization.dto.JWT;
import org.example.authorization.dto.RefreshToken;
import org.example.authorization.dto.UserPrincipal;
import org.example.authorization.service.JWTService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    private final UserDetailsService userDetailsService;
    private final String secretKey;

    public JWTServiceImpl(UserDetailsService userDetailsService) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGenerator.generateKey();
        secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        this.userDetailsService = userDetailsService;
    }

    public JWT generateToken(String subject, UUID uuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", uuid.toString());
        JWT jwt = new JWT();
        jwt.setAccessToken(Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .and()
                .signWith(getSecretKey())
                .compact());
        jwt.setRefreshToken(Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .and()
                .signWith(getSecretKey())
                .compact());

        return jwt;
    }

    public String generateAccessToken(String user, UUID uuid, RefreshToken refreshToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user);
        if (validateToken(refreshToken.getToken(), (UserPrincipal) userDetails)) {
            return generateToken(user, uuid).getAccessToken();
        }
        return null;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractUUID(String token) {
        return UUID.fromString(extractAllClaims(token).get("uuid", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }

    public boolean validateToken(String token, UserPrincipal userDetails) {
        final String userName = extractUsername(token);
        final UUID uuid = extractUUID(token);
        return (userName.equals(userDetails.getUsername()) && userDetails.getUUID().equals(uuid) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
