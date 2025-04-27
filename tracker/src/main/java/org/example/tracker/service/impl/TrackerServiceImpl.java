package org.example.tracker.service.impl;

import lombok.AllArgsConstructor;
import org.example.tracker.entity.Category;
import org.example.tracker.entity.Company;
import org.example.tracker.entity.Product;
import org.example.tracker.entity.UserTracker;
import org.example.tracker.repository.CategoryRepository;
import org.example.tracker.repository.CompanyRepository;
import org.example.tracker.repository.ProductRepository;
import org.example.tracker.repository.UserTrackerRepository;
import org.example.tracker.service.TrackerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TrackerServiceImpl implements TrackerService {

    private final ProductRepository productRepository;
    private final UserTrackerRepository userTrackerRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public void addProduct(UUID userUuid, UUID productUuid) {
        UserTracker userTracker = userTrackerRepository.findByUuid(userUuid);
        if (!userTracker.getProductsUuid().contains(productUuid)) {
            Map<UUID, List<UUID>> tracker = parseTracker(new JSONObject(userTracker.getTracker()));
            Product product = productRepository.findByUuid(productUuid);
            if (findCategoryExists(tracker, product)) {tracker.put(productUuid, new ArrayList<>());}
            userTracker.getBansUuid().remove(productUuid);
            userTracker.setTracker(new JSONObject(tracker).toString());
            userTracker.getProductsUuid().add(productUuid);
            userTrackerRepository.save(userTracker);
        }
    }

    public void addCompany(UUID userUuid, UUID companyUuid) {
        UserTracker userTracker = userTrackerRepository.findByUuid(userUuid);
        if (!userTracker.getCompaniesUuid().contains(companyUuid)) {
            Company company = companyRepository.findByUuid(companyUuid);
            List<UUID> productsUuid = company.getProductsUuid();
            for (UUID productUuid : productsUuid) {
                addProduct(userUuid, productUuid);
            }
            userTracker.getCompaniesUuid().add(companyUuid);
            userTrackerRepository.save(userTracker);
        }
    }

    public void moveProduct(UUID userUuid, UUID productUuid, UUID categoryUuid) {
        UserTracker userTracker = userTrackerRepository.findByUuid(userUuid);
        Map<UUID, List<UUID>> tracker = parseTracker(new JSONObject(userTracker.getTracker()));
        for (UUID key : tracker.keySet()) {
            tracker.get(key).remove(productUuid);
        }
        tracker.get(categoryUuid).add(productUuid);
        userTracker.setTracker(new JSONObject(tracker).toString());
        userTrackerRepository.save(userTracker);
    }

    public void banProduct(UUID userUuid, UUID productUuid) {
        UserTracker userTracker = userTrackerRepository.findByUuid(userUuid);
        Product product = productRepository.findByUuid(productUuid);
        if (userTracker.getCompaniesUuid().contains(product.getCompanyUuid())) {
            userTracker.getBansUuid().add(productUuid);
        } else {
            userTracker.getProductsUuid().remove(productUuid);
        }
        userTrackerRepository.save(userTracker);
    }

    public void getTrackers(UUID userUuid) {

    }

    private Map<UUID, List<UUID>> parseTracker(JSONObject json) {
        Map<UUID, List<UUID>> tracker = new HashMap<>();
        json.keys().forEachRemaining(key -> {
            JSONArray value = (JSONArray) json.get(key);
            tracker.put(UUID.fromString(key), parseTrackedProduct(value));
        });
        return tracker;
    }

    private List<UUID> parseTrackedProduct(JSONArray array) {
        List<UUID> trackerProduct = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object item = array.get(i);
            trackerProduct.add(UUID.fromString(item.toString()));
        }
        return trackerProduct;
    }

    private boolean findCategoryExists(Map<UUID, List<UUID>> tracker, Product product) {
        for (UUID key : tracker.keySet()) {
            if (key.equals(product.getCategory())) {
                tracker.get(key).add(product.getUuid());
                return false;
            }
        }
        return true;
    }

}
