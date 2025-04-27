package org.example.scrap.controller;


import lombok.AllArgsConstructor;
import org.example.scrap.entity.ProductOrder;
import org.example.scrap.entity.CompanyOrder;
import org.example.scrap.repository.ProductOrderRepository;
import org.example.scrap.repository.CompanyOrderRepository;
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
    CompanyOrderRepository siteOrderRepository;

    @PostMapping("/product")
    public ResponseEntity<?> crateProductOrder(@RequestBody ProductOrder productOrder, @RequestHeader("X-Email") String email,
                                        @RequestHeader("X-UUID") UUID userUuid) {
        orderService.createProductOrder(productOrder, email, userUuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/company")
    public ResponseEntity<?> crateCompanyOrder(@RequestBody CompanyOrder companyOrder, @RequestHeader("X-Email") String email,
                                        @RequestHeader("X-UUID") UUID userUuid) {
        orderService.createSiteOrder(companyOrder, email, userUuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/accepted-company")
    public ResponseEntity<?> acceptCompanyOrder(@RequestBody Long id) {
        orderService.acceptedCompanyOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/accepted-product")
    public ResponseEntity<?> acceptProductOrder(@RequestBody Long id) {
        orderService.acceptedProductOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/products")
    public List<ProductOrder> getProducts() {
        return productOrderRepository.findAll();
    }

    @GetMapping("/companies")
    public List<CompanyOrder> getCompanies() {
        return siteOrderRepository.findAll();
    }

}
