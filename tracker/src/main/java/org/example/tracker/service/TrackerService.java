package org.example.tracker.service;

import org.example.tracker.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface TrackerService {

    void addProduct(UUID userUuid, UUID productUuid);

    void addCompany(UUID userUuid, UUID companyUuid);

    void moveProduct(UUID userUuid, UUID productUuid, UUID categoryUuid);

    void banProduct(UUID userUuid, UUID productUuid);

    HashMap<String, List<Product>> getTracker(UUID userUuid);

    void createUser(UUID userUuid);

}
