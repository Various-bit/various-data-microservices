package org.example.authorization.repository;

import org.example.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String username);

    boolean existsByUsername(String email);

    Optional<User> findByEmailOrUsername(String email, String username);

    Optional<User> findByUsername(String username);
}
