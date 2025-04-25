package org.example.scrap.service.impl;

import lombok.AllArgsConstructor;
import org.example.scrap.entity.ProductOrder;
import org.example.scrap.entity.SiteOrder;
import org.example.scrap.repository.ProductOrderRepository;
import org.example.scrap.repository.SiteOrderRepository;
import org.example.scrap.service.OrderService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    ProductOrderRepository productOrderRepository;
    SiteOrderRepository siteOrderRepository;

    public void createSiteOrder(SiteOrder siteOrder, String email, UUID userUuid) {
        siteOrder.setCreateDate(new Date(System.currentTimeMillis()));
        siteOrder.setEmail(email);
        siteOrder.setUserUuid(userUuid);
        siteOrder.setUuid(UUID.randomUUID());
        siteOrderRepository.save(siteOrder);
    }

    public void createProductOrder(ProductOrder productOrder, String email, UUID userUuid) {
        productOrder.setCreateDate(new Date(System.currentTimeMillis()));
        productOrder.setEmail(email);
        productOrder.setUserUuid(userUuid);
        productOrder.setUuid(UUID.randomUUID());
        productOrderRepository.save(productOrder);
    }

    public void acceptedProductOrder(Long id) {
        productOrderRepository.findById(id).ifPresent(order -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://tracker:8080/api/accepted-product"))
                    .header("X-UUID", order.getUserUuid().toString())
                    .header("X-UUIDProduct", order.getUuid().toString())
                    .build();
            try {
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void acceptedSiteOrder(Long id) {
        siteOrderRepository.findById(id).ifPresent(order -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://tracker:8080/api/accepted-site"))
                    .header("X-UUID", order.getUserUuid().toString())
                    .header("X-UUIDSite", order.getUuid().toString())
                    .build();
            try {
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
