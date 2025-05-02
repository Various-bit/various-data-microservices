package org.example.tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "user_tracker")
public class UserTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "companies_uuid")
    @Convert(converter = UUIDListConverter.class)
    private List<UUID> companiesUuid = new ArrayList<>();

    @Column(name = "tracker")
    private String tracker;

    @Column(name = "products_uuid")
    @Convert(converter = UUIDListConverter.class)
    private List<UUID> productsUuid = new ArrayList<>();

    @Column(name = "bans_uuid")
    @Convert(converter = UUIDListConverter.class)
    private List<UUID> bansUuid = new ArrayList<>();

    public UserTracker() {}

    public UserTracker(UUID uuid) {
        this.uuid = uuid;
        this.tracker = "";
    }
}
