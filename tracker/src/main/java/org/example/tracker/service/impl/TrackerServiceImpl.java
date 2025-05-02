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

    public void createUser(UUID userUuid) {
        UserTracker userTracker = new UserTracker(userUuid);
        userTrackerRepository.save(userTracker);
    }

    public void addProduct(UUID userUuid, UUID productUuid) {
        UserTracker userTracker = userTrackerRepository.findByUuid(userUuid);
        if (!userTracker.getProductsUuid().contains(productUuid)) {
            System.out.println("------------------" + userTracker.getTracker() + "---------------------");
            Map<UUID, List<UUID>> tracker = parseTracker(new JSONObject(userTracker.getTracker()));
            Product product = productRepository.findByUuid(productUuid);
            if (findCategoryExists(tracker, product)) {
                tracker.put(product.getCategory(), new ArrayList<>());
                tracker.get(product.getCategory()).add(product.getUuid());
            }
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

    public HashMap<String, List<Product>> getTracker(UUID userUuid) {
        UserTracker userTracker = userTrackerRepository.findByUuid(userUuid);
        Map<UUID, List<UUID>> trackerUuid = parseTracker(new JSONObject(userTracker.getTracker()));
        HashMap<String, List<Product>> tracker = new HashMap<>();
        for (UUID categoryUuid : trackerUuid.keySet()) {
            parseProductsCategory(tracker, trackerUuid, categoryUuid);
        }
        return tracker;
    }

    private void parseProductsCategory(HashMap<String, List<Product>> tracker, Map<UUID, List<UUID>> trackerUuid,
                                       UUID categoryUuid) {
        List<Product> categoryProducts = new ArrayList<>();
        String category = categoryRepository.findByUuid(categoryUuid).getLabel();
        trackerUuid.get(categoryUuid).forEach(productUuid -> {
            categoryProducts.add(productRepository.findByUuid(productUuid));
        });
        tracker.put(category, categoryProducts);
    }

    private Map<UUID, List<UUID>> parseTracker(JSONObject json) {
        Map<UUID, List<UUID>> tracker = new HashMap<>();
        json.keys().forEachRemaining(key -> {
            System.out.println(key);
            System.out.println(key.getClass());
            JSONArray value = (JSONArray) json.get(key);
            tracker.put(UUID.fromString(key), parseTrackedProduct(value));
        });
        return tracker;
    }

    private List<UUID> parseTrackedProduct(JSONArray array) {
        List<UUID> trackerProduct = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String item = (String) array.get(i);
            System.out.println(item);
            trackerProduct.add(UUID.fromString(item));
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
