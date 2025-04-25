package org.example.tracker.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface TrackerService {

    void addProduct(UUID userUuid, UUID productUuid);

    void addSite(UUID userUuid, UUID siteUuid);

}
