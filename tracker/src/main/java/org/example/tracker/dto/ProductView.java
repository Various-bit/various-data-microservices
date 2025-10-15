package org.example.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.tracker.entity.Product;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductView {
    private String label;
    private String companyName;
    private Double currentPrice;
    private Double discount;
    private List<Double> prices;
    private List<Date> priceChangeDates;
    private Boolean availability;
    private Date dateOfLoss;
    private List<String> regions;

    public ProductView(Product product, String companyName) {
        this.label = product.getLabel();
        this.companyName = companyName;
        this.currentPrice = product.getCurrentPrice();
        this.discount = product.getDiscount();
        this.prices = product.getPrices();
        this.priceChangeDates = product.getPriceChangeDates();
        this.availability = product.getAvailability();
        this.dateOfLoss = product.getDateOfLoss();
        this.regions = product.getRegions();
    }

}
