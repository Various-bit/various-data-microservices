package org.example.scrap.service;

import org.example.scrap.entity.ProductOrder;
import org.example.scrap.entity.SiteOrder;

import java.io.IOException;
import java.util.UUID;

public interface OrderService {

    void createSiteOrder(SiteOrder siteOrder, String email, UUID userUuid);

    void createProductOrder(ProductOrder productOrder, String email, UUID userUuid);

    void acceptedProductOrder(Long id);

    void acceptedSiteOrder(Long id);

}
