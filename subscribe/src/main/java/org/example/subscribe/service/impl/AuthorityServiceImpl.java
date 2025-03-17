package org.example.subscribe.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.subscribe.entity.Authority;
import org.example.subscribe.entity.UserAuthority;
import org.example.subscribe.repository.AuthorityRepository;
import org.example.subscribe.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Override
    public void saveAuthority(UUID uuid) {
        authorityRepository.save(new UserAuthority(uuid));
    }

    @Override
    public String getAuthority(UUID uuid) {
        UserAuthority userAuthority = authorityRepository.getUserAuthorityByUuid(uuid);
        if (userAuthority.getAuthority().equals(Authority.SUBSCRIBER.name()) &&
        userAuthority.getDate().after(new Date(System.currentTimeMillis()))) {
            userAuthority.setAuthority(Authority.USER.name());
            authorityRepository.save(userAuthority);
        }
        return userAuthority.getAuthority();
    }
}
