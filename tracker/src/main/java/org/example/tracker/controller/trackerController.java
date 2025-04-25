package org.example.tracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class trackerController {

    @RequestMapping("/accepted-product")
    public ResponseEntity<?> acceptedProduct(@RequestHeader("X-UUID") UUID userUuid,
                                             @RequestHeader("X-UUIDProduct") UUID productUuid) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping("/accepted-site")
    public ResponseEntity<?> acceptedSite(@RequestHeader("X-UUID") UUID userUuid,
                                             @RequestHeader("X-UUIDSite") UUID siteUuid) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
