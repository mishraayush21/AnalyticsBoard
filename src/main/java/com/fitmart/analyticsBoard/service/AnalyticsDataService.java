package com.fitmart.analyticsBoard.service;

import com.fitmart.analyticsBoard.model.AnalyticsData;
import com.fitmart.analyticsBoard.repository.AnalyticsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnalyticsDataService {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private AnalyticsDataRepository analyticsDataRepository;

    public Map<String, AnalyticsData> getAllAnalyticsData() {
        Map<String, AnalyticsData> analyticsDataMap = new HashMap<>();
        if (Objects.requireNonNull(redisTemplate.keys("*")).isEmpty()) {
            populateCacheFromDatabase();
        }

        for (String key : Objects.requireNonNull(redisTemplate.keys("*"))) {
            String productId = extractProductId(key);
            int views = getMetric(productId, "views");
            int clicks = getMetric(productId, "clicks");
            int sales = getMetric(productId, "sales");

            AnalyticsData analyticsData = new AnalyticsData(productId, views, clicks, sales);
            analyticsDataMap.put(productId, analyticsData);
        }

        return analyticsDataMap;
    }

    private void populateCacheFromDatabase() {
        List<AnalyticsData> allData = analyticsDataRepository.findAll();
        for (AnalyticsData data : allData) {
            storeInCache(data.getProductId(), data);
        }
    }

    private void storeInCache(String productId, AnalyticsData analyticsData) {
        String viewsKey = getKey(productId, "views");
        redisTemplate.opsForValue().set(viewsKey, analyticsData.getViews());

        String clicksKey = getKey(productId, "clicks");
        redisTemplate.opsForValue().set(clicksKey, analyticsData.getClicks());

        String salesKey = getKey(productId, "sales");
        redisTemplate.opsForValue().set(salesKey, analyticsData.getSales());
    }

    private String extractProductId(String key) {
        int index = key.indexOf(":");
        if (index != -1) {
            return key.substring(0, index);
        } else {
            return key;
        }
    }

    public void incrementCount(String productId, int views, int clicks, int sales) {
        incrementMetric(productId, "views", views);
        incrementMetric(productId, "clicks", clicks);
        incrementMetric(productId, "sales", sales);
    }

    private void incrementMetric(String productId, String metricName, int incrementBy) {
        String key = getKey(productId, metricName);
        redisTemplate.opsForValue().increment(key, incrementBy);
    }

    private int getMetric(String productId, String metricName) {
        String key = getKey(productId, metricName);
        Integer value = redisTemplate.opsForValue().get(key);
        return (value != null) ? value : 0;
    }

    private String getKey(String productId, String metricName) {
        return productId + ":" + metricName;
    }

    public void bulkInsertToMySQL() {
        for (String key : Objects.requireNonNull(redisTemplate.keys("*"))) {
            String productId = extractProductId(key);
            int views = getMetric(productId, "views");
            int clicks = getMetric(productId, "clicks");
            int sales = getMetric(productId, "sales");

            AnalyticsData analyticsData = analyticsDataRepository.findByProductId(productId);

            if (analyticsData != null) {
                analyticsData.setViews(views);
                analyticsData.setClicks(clicks);
                analyticsData.setSales(sales);
            } else {
                analyticsData = new AnalyticsData(productId, views, clicks, sales);
            }
            analyticsDataRepository.save(analyticsData);
        }
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*")));
    }
}