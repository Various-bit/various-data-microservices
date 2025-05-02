package org.example.tracker.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

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
    @Convert(converter = DoubleListConverter.class)
    private List<Double> prices = new ArrayList<>();

    @Column(name = "price_change_dates")
    @Convert(converter = SqlDateListConverter.class)
    private List<Date> priceChangeDates = new ArrayList<>();

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "date_of_loss")
    private Date dateOfLoss;

    @Column(name = "regions")
    @Convert(converter = StringListConverter.class)
    private List<String> regions = new ArrayList<>();

    @Column(name="category")
    private UUID category;

}


