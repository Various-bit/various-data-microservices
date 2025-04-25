package org.example.scrap.controller;


import lombok.AllArgsConstructor;
import org.example.scrap.entity.ProductOrder;
import org.example.scrap.entity.SiteOrder;
import org.example.scrap.repository.ProductOrderRepository;
import org.example.scrap.repository.SiteOrderRepository;
import org.example.scrap.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/scrap")
@AllArgsConstructor
public class ProductController {

    OrderService orderService;
    ProductOrderRepository productOrderRepository;
    SiteOrderRepository siteOrderRepository;

    @PostMapping("/product")
    public ResponseEntity<?> crateOrder(@RequestBody ProductOrder productOrder, @RequestHeader("X-Email") String email,
                                        @RequestHeader("X-UUID") UUID userUuid) {
        orderService.createProductOrder(productOrder, email, userUuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/site")
    public ResponseEntity<?> crateOrder(@RequestBody SiteOrder siteOrder, @RequestHeader("X-Email") String email,
                                        @RequestHeader("X-UUID") UUID userUuid) {
        orderService.createSiteOrder(siteOrder, email, userUuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/accepted-site")
    public ResponseEntity<?> acceptSiteOrder(@RequestBody Long id) {
        orderService.acceptedSiteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/accepted-product")
    public ResponseEntity<?> acceptProductOrder(@RequestBody Long id) {
        orderService.acceptedProductOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/products")
    public List<ProductOrder> getOrders() {
        return productOrderRepository.findAll();
    }

    @GetMapping("/sites")
    public List<SiteOrder> getSites() {
        return siteOrderRepository.findAll();
    }

}
