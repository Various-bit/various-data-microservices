package org.example.subscribe.repository;

import org.example.subscribe.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<UserAuthority, Long> {
    UserAuthority getUserAuthorityByUuid(UUID uuid);
}
