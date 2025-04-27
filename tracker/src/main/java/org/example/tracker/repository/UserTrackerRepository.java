package org.example.tracker.repository;

import org.example.tracker.entity.UserTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTrackerRepository extends JpaRepository<UserTracker, Long> {
    UserTracker findByUuid(UUID uuid);
}
