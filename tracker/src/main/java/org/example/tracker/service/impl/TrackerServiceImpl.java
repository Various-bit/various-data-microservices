package org.example.tracker.service.impl;

import lombok.AllArgsConstructor;
import org.example.tracker.dto.ProductView;
import org.example.tracker.dto.Search;
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
            Company company = companyRepository.findByUuid(companyUuid).orElse(null);
            assert company != null;
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
        Map<UUID, List<UUID>> tracker = parseTracker(new JSONObject(userTracker.getTracker()), productUuid);
        if (userTracker.getCompaniesUuid().contains(product.getCompanyUuid())) {
            userTracker.getBansUuid().add(productUuid);
        } else {
            System.out.println(userTracker.getProductsUuid());
            userTracker.getProductsUuid().remove(productUuid);
        }
        userTracker.setTracker(new JSONObject(tracker).toString());
        userTrackerRepository.save(userTracker);
    }

    public HashMap<String, List<ProductView>> getTracker(UUID userUuid) {
        UserTracker userTracker = userTrackerRepository.findByUuid(userUuid);
        Map<UUID, List<UUID>> trackerUuid = parseTracker(new JSONObject(userTracker.getTracker()));
        HashMap<String, List<ProductView>> tracker = new HashMap<>();
        for (UUID categoryUuid : trackerUuid.keySet()) {
            parseProductsCategory(tracker, trackerUuid, categoryUuid);
        }
        return tracker;
    }

    public List<Search> getProductList(UUID userUuid) {
        List<Product> products = productRepository.findAll();
        List<Search> searches = new ArrayList<>();
        List<UUID> alreadyProducts = userTrackerRepository.findByUuid(userUuid).getProductsUuid();
        products.forEach(product -> searches.add(new Search(product.getLabel(),
                companyRepository.findByUuid(product.getCompanyUuid())
                        .map(Company::getName).orElse(null),
                product.getUuid(), alreadyProducts.contains(product.getUuid())))
        );
        return searches;
    }

    private void parseProductsCategory(HashMap<String, List<ProductView>> tracker, Map<UUID, List<UUID>> trackerUuid,
                                       UUID categoryUuid) {
        List<ProductView> categoryProducts = new ArrayList<>();
        String category = categoryRepository.findByUuid(categoryUuid).getLabel();
        trackerUuid.get(categoryUuid).forEach(productUuid -> {
            Product product = productRepository.findByUuid(productUuid);
            Company company = companyRepository.findByUuid(product.getCompanyUuid()).orElse(null);
            categoryProducts.add(new ProductView(product,
                    company != null ? company.getName() : "Not found"));
        });
        tracker.put(category, categoryProducts);
    }

    private Map<UUID, List<UUID>> parseTracker(JSONObject json) {
        Map<UUID, List<UUID>> tracker = new HashMap<>();
        json.keys().forEachRemaining(key -> {
            JSONArray value = (JSONArray) json.get(key);
            tracker.put(UUID.fromString(key), parseTrackedProduct(value));
        });
        return tracker;
    }

    private Map<UUID, List<UUID>> parseTracker(JSONObject json, UUID exceptionUuid) {
        Map<UUID, List<UUID>> tracker = new HashMap<>();
        json.keys().forEachRemaining(key -> {
            JSONArray value = (JSONArray) json.get(key);
            List<UUID> trackedProduct = parseTrackedProduct(value, exceptionUuid);
            if (!trackedProduct.isEmpty()) {
                tracker.put(UUID.fromString(key), parseTrackedProduct(value, exceptionUuid));
            }
        });
        return tracker;
    }

    private List<UUID> parseTrackedProduct(JSONArray array) {
        List<UUID> trackerProduct = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String item = (String) array.get(i);
            trackerProduct.add(UUID.fromString(item));
        }
        return trackerProduct;
    }

    private List<UUID> parseTrackedProduct(JSONArray array, UUID exceptionUuid) {
        List<UUID> trackerProduct = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String item = (String) array.get(i);
            if (!UUID.fromString(item).equals(exceptionUuid)) {
                trackerProduct.add(UUID.fromString(item));
            }
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
