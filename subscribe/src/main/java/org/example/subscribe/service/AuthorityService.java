package org.example.subscribe.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

public interface AuthorityService {
    void saveAuthority(UUID uuid);

    String getAuthority(UUID uuid);
}
