package org.example.tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "user_tracked")
public class UserTracked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "companies_id")
    @ElementCollection
    private List<UUID> companiesUuid = new ArrayList<>();

    @Column(name = "tracker")
    @ElementCollection
    private String tracker;

    @Column(name = "products_uuid")
    @ElementCollection
    private List<UUID> productsUuid = new ArrayList<>();

    @Column(name = "bans_uuid")
    @ElementCollection
    private List<UUID> bansUuid = new ArrayList<>();

}
