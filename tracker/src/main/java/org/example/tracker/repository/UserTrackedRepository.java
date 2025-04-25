package org.example.tracker.repository;

import org.example.tracker.entity.UserTracked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserTrackedRepository extends JpaRepository<UserTracked, Long> {
    UserTracked findByUuid(UUID uuid);
}
