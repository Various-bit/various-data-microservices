package org.example.scrap.service;

import org.example.scrap.entity.ProductOrder;
import org.example.scrap.entity.CompanyOrder;

import java.util.UUID;

public interface OrderService {

    void createSiteOrder(CompanyOrder siteOrder, String email, UUID userUuid);

    void createProductOrder(ProductOrder productOrder, String email, UUID userUuid);

    void acceptedProductOrder(Long id);

    void acceptedCompanyOrder(Long id);

}
