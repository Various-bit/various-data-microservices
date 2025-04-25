package org.example.scrap.repository;

import org.example.scrap.entity.SiteOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteOrderRepository extends JpaRepository<SiteOrder, Long> {
}
