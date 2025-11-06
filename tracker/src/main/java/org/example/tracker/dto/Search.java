package org.example.tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Search {

    private String label;
    private String companyLabel;
    private UUID uuid;
    private Boolean already;

    public Search(String label, String companyLabel, UUID uuid, Boolean already) {
        this.label = label;
        this.companyLabel = companyLabel;
        this.uuid = uuid;
        this.already = already;
    }
}
