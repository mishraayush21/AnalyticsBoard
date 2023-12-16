package com.fitmart.analyticsBoard.service;

import com.fitmart.analyticsBoard.model.AnalyticsData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsDataService {

    private final Map<String, AnalyticsData> analyticsDataMap = new HashMap<>();

    public void updateAnalyticsData(AnalyticsData analyticsData) {
        String productId = analyticsData.getProductId();
        if (analyticsDataMap.containsKey(productId)) {
            AnalyticsData existingData = analyticsDataMap.get(productId);
            existingData.setViews(existingData.getViews() + analyticsData.getViews());
            existingData.setSales(existingData.getSales() + analyticsData.getSales());
            existingData.setClicks(existingData.getClicks() + analyticsData.getClicks());
        } else {
            analyticsDataMap.put(productId, analyticsData);
        }
    }

    public AnalyticsData getAnalyticsData(String productId) {
        return analyticsDataMap.get(productId);
    }

    public Map<String, AnalyticsData> getAllAnalyticsData() {
        return new HashMap<>(analyticsDataMap);
    }

    public void incrementViews(String productId, int count) {
        if (analyticsDataMap.containsKey(productId)) {
            AnalyticsData existingData = analyticsDataMap.get(productId);
            existingData.setViews(existingData.getViews() + count);
        } else {
            AnalyticsData newData = new AnalyticsData(productId, count, 0, 0);
            analyticsDataMap.put(productId, newData);
        }
    }

    public void incrementCLickCount(String productId, int count) {
        if (analyticsDataMap.containsKey(productId)) {
            AnalyticsData existingData = analyticsDataMap.get(productId);
            existingData.setClicks(existingData.getClicks() + count);
        } else {
            AnalyticsData newData = new AnalyticsData(productId, 0, count, 0);
            analyticsDataMap.put(productId, newData);
        }
    }
    public void incrementSaleCount(String productId, int count) {
        if (analyticsDataMap.containsKey(productId)) {
            AnalyticsData existingData = analyticsDataMap.get(productId);
            existingData.setSales(existingData.getSales() + count);
        } else {
            AnalyticsData newData = new AnalyticsData(productId, 0, 0, count);
            analyticsDataMap.put(productId, newData);
        }
    }
}
