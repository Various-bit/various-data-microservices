package org.example.tracker.repository;

import org.example.tracker.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByUuid(UUID uuid);
}
