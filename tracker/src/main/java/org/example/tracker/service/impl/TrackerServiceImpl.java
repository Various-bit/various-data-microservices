package org.example.tracker.service.impl;

import lombok.AllArgsConstructor;
import org.example.tracker.entity.Company;
import org.example.tracker.entity.UserTracked;
import org.example.tracker.repository.CompanyRepository;
import org.example.tracker.repository.ProductRepository;
import org.example.tracker.repository.UserTrackedRepository;
import org.example.tracker.service.TrackerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrackerServiceImpl implements TrackerService {

    private final ProductRepository productRepository;
    private final UserTrackedRepository userTrackedRepository;
    private final CompanyRepository companyRepository;

    public void addProduct(UUID userUuid, UUID productUuid) {

    }

    public void addSite(UUID userUuid, UUID siteUuid) {

    }
}
