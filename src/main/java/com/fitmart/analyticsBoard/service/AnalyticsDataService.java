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

    public void updateAnalyticsData(AnalyticsData analyticsData) {
        String productId = analyticsData.getProductId();
        AnalyticsData existingData = analyticsDataRepository.findByProductId(productId);

        if (existingData != null) {
            existingData.setViews(existingData.getViews() + analyticsData.getViews());
            existingData.setSales(existingData.getSales() + analyticsData.getSales());
            existingData.setClicks(existingData.getClicks() + analyticsData.getClicks());
        } else {
            analyticsDataRepository.save(analyticsData);
        }

        calculateAndStoreMetrics(analyticsData);
    }

    private void calculateAndStoreMetrics(AnalyticsData analyticsData) {
        String productId = analyticsData.getProductId();
        AnalyticsData data = analyticsDataRepository.findByProductId(productId);
        data.setCtr((data.getViews() != 0) ? (double) data.getClicks() / data.getViews() : 0);
        data.setConversionRate((data.getClicks() != 0) ? (double) data.getSales() / data.getClicks() : 0);

        // Save the updated metrics back to the database
        analyticsDataRepository.save(data);
    }

    public AnalyticsData getAnalyticsData(String productId) {
        return analyticsDataRepository.findByProductId(productId);
    }

    public Map<String, AnalyticsData> getAllAnalyticsData() {
        Map<String, AnalyticsData> analyticsDataMap = new HashMap<>();
        analyticsDataRepository.findAll().forEach(analyticsData -> analyticsDataMap.put(analyticsData.getProductId(), analyticsData));
        return analyticsDataMap;
    }

    @Transactional
    public void incrementViews(String productId, int count) {
        AnalyticsData existingData = analyticsDataRepository.findByProductId(productId);

        if (existingData != null) {
            existingData.setViews(existingData.getViews() + count);
            calculateAndStoreMetrics(existingData);
        } else {
            AnalyticsData newData = new AnalyticsData(productId, count, 0, 0);
            analyticsDataRepository.save(newData);
        }
    }

    @Transactional
    public void incrementClickCount(String productId, int count) {
        AnalyticsData existingData = analyticsDataRepository.findByProductId(productId);

        if (existingData != null) {
            existingData.setClicks(existingData.getClicks() + count);
            calculateAndStoreMetrics(existingData);
        } else {
            AnalyticsData newData = new AnalyticsData(productId, 0, count, 0);
            analyticsDataRepository.save(newData);
        }
    }

    @Transactional
    public void incrementSaleCount(String productId, int count) {
        AnalyticsData existingData = analyticsDataRepository.findByProductId(productId);

        if (existingData != null) {
            existingData.setSales(existingData.getSales() + count);
            calculateAndStoreMetrics(existingData);
        } else {
            AnalyticsData newData = new AnalyticsData(productId, 0, 0, count);
            analyticsDataRepository.save(newData);
        }
    }
}
