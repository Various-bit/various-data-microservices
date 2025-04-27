package org.example.tracker.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name="label")
    private String label;

    @Column(name = "company_id")
    private UUID companyUuid;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "prices")
    @ElementCollection
    private List<Double> prices;

    @Column(name = "price_change_dates")
    @ElementCollection
    private List<Date> priceChangeDates;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "date_of_loss")
    private Date dateOfLoss;

    @Column(name = "regions")
    @ElementCollection
    private List<String> regions;

    @Column(name="category")
    private UUID category;

}
