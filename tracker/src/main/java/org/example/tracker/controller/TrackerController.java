package org.example.tracker.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.example.tracker.entity.Product;
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
    public ResponseEntity<?> moveProduct(@RequestHeader("X-UUID") UUID userUuid, @RequestBody UUID productUuid,
                                         @RequestBody UUID categoryUuid) {
        trackerService.moveProduct(userUuid, productUuid, categoryUuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/ban-product")
    public ResponseEntity<?> banProduct(@RequestHeader("X-UUID") UUID userUuid,@RequestBody UUID productUuid) {
        trackerService.banProduct(userUuid, productUuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/tracker")
    public HashMap<Product, List<Product>> getTracker(@RequestHeader("X-UUID") UUID userUuid) {

    }

}
