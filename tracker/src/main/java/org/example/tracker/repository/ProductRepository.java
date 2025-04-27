package org.example.tracker.repository;

import org.example.tracker.entity.Product;
import org.example.tracker.entity.UserTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByUuid(UUID uuid);
}
