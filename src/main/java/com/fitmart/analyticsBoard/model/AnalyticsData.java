package com.fitmart.analyticsBoard.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "analytics_data")
@Getter
@Setter
@Data
public class AnalyticsData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "views")
    private int views;

    @Column(name = "sales")
    private int sales;

    @Column(name = "clicks")
    private int clicks;

    @Column(name = "ctr")
    private double ctr;

    @Column(name = "conversion_rate")
    private double conversionRate;

    // Getters and setters

    public AnalyticsData() {
        // Default constructor
    }

    public AnalyticsData(String productId, int views, int sales, int clicks) {
        this.productId = productId;
        this.views = views;
        this.sales = sales;
        this.clicks = clicks;
        this.ctr = ( views != 0 ) ? (double) clicks / views : 0;
        this.conversionRate = ( clicks != 0 ) ? (double) sales / clicks : 0;
    }

}
