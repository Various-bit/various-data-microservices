package org.example.scrap.repository;

import org.example.scrap.entity.CompanyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyOrderRepository extends JpaRepository<CompanyOrder, Long> {
}
