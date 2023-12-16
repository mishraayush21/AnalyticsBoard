package com.fitmart.analyticsBoard.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyticsData {
    private String productId;
    private int views;
    private int sales;
    private int clicks;
    private double ctr;
    private double conversionRate;

    public AnalyticsData(String productId, int views, int sales, int clicks) {
        this.productId = productId;
        this.views = views;
        this.sales = sales;
        this.clicks = clicks;
        this.ctr = ( views != 0 ) ? (double) clicks / views : 0;
        this.conversionRate = ( clicks != 0 ) ? (double) sales / clicks : 0;
    }


    public AnalyticsData() {
    }


    @Override
    public String toString() {
        return "AnalyticsData{" +
                "productId='" + productId + '\'' +
                ", views=" + views +
                ", sales=" + sales +
                ", clicks=" + clicks +
                ", ctr=" + ctr +
                ", conversionRate=" + conversionRate +
                '}';
    }
}
