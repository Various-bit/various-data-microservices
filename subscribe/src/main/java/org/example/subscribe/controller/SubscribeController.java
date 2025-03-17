package org.example.subscribe.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.subscribe.service.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscribe")
@AllArgsConstructor
public class SubscribeController {

    private final AuthorityService authorityService;

    @GetMapping("/signup")
    public ResponseEntity<?> subscribe(@RequestHeader("X-UUID") UUID uuid) {
        authorityService.saveAuthority(uuid);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/authority")
    public ResponseEntity<?> authority(@RequestHeader("X-UUID") UUID uuid, HttpServletResponse response) {
        String authority = authorityService.getAuthority(uuid);
        response.addHeader("X-Authority", authority);
        return ResponseEntity.ok().build();
    }

}
