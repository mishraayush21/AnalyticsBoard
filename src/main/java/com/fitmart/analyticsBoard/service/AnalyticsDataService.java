package com.fitmart.analyticsBoard.service;

import com.fitmart.analyticsBoard.model.AnalyticsData;
import com.fitmart.analyticsBoard.repository.AnalyticsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsDataService {

    @Autowired
    private AnalyticsDataRepository analyticsDataRepository;

    public Map<String, AnalyticsData> getAllAnalyticsData() {
        Map<String, AnalyticsData> analyticsDataMap = new HashMap<>();
        analyticsDataRepository.findAll().forEach(analyticsData -> analyticsDataMap.put(analyticsData.getProductId(), analyticsData));
        return analyticsDataMap;
    }

    @Transactional
    public void incrementCount(String productId, int views, int clicks, int sales) {
        AnalyticsData existingData = analyticsDataRepository.findByProductId(productId);
        if (existingData != null) {
            existingData.setViews(existingData.getViews() + views);
            existingData.setClicks(existingData.getClicks() + clicks);
            existingData.setSales(existingData.getSales() + sales);
            calculateAndStoreMetrics(existingData);
        } else {
            AnalyticsData newData = new AnalyticsData(productId, views, clicks, sales);
            analyticsDataRepository.save(newData);
        }
    }

    private void calculateAndStoreMetrics(AnalyticsData analyticsData) {
        String productId = analyticsData.getProductId();
        AnalyticsData data = analyticsDataRepository.findByProductId(productId);
        data.setCtr((data.getViews() != 0) ? (double) data.getClicks() / data.getViews() : 0);
        data.setConversionRate((data.getClicks() != 0) ? (double) data.getSales() / data.getClicks() : 0);
        analyticsDataRepository.save(data);
    }
}