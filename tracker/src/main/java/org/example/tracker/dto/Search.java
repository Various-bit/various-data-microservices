package org.example.tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Search {

    String label;
    UUID uuid;

    public Search(String label, UUID uuid) {
        this.label = label;
        this.uuid = uuid;
    }
}
