package org.example.tracker.controller;

import lombok.AllArgsConstructor;
import org.example.tracker.dto.BanProduct;
import org.example.tracker.dto.MoveProduct;
import org.example.tracker.dto.ProductView;
import org.example.tracker.dto.Search;
import org.example.tracker.entity.Product;
import org.example.tracker.entity.UserTracker;
import org.example.tracker.service.TrackerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TrackerController {

    TrackerService trackerService;

    @RequestMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestHeader("X-UUID") UUID userUuid) {
        trackerService.createUser(userUuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping("/add-product")
    public ResponseEntity<?> acceptedProduct(@RequestHeader("X-UUID") UUID userUuid,
                                             @RequestHeader("X-UUIDProduct") UUID productUuid) {
        trackerService.addProduct(userUuid, productUuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping("/add-company")
    public ResponseEntity<?> acceptedSite(@RequestHeader("X-UUID") UUID userUuid,
                                             @RequestHeader("X-UUIDCompany") UUID companyUuid) {
        trackerService.addCompany(userUuid, companyUuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/move-product")
    public ResponseEntity<?> moveProduct(@RequestHeader("X-UUID") UUID userUuid, @RequestBody MoveProduct moveProduct) {
        trackerService.moveProduct(userUuid, moveProduct.getProductUuid(), moveProduct.getCategoryUuid());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


//    TODO: ban-product not worked

    @PostMapping("/ban-product")
    public ResponseEntity<?> banProduct(@RequestHeader("X-UUID") UUID userUuid, @RequestBody BanProduct banProduct) {
        trackerService.banProduct(userUuid, banProduct.getProductUuid());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/tracker")
    public HashMap<String, List<ProductView>> getTracker(@RequestHeader("X-UUID") UUID userUuid) {
        return trackerService.getTracker(userUuid);
    }

    @GetMapping("/product-list")
    public List<Search> getProductList(@RequestHeader("X-UUID") UUID userUuid) {
        return trackerService.getProductList(userUuid);
    }

}
