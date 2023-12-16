package com.fitmart.analyticsBoard.service;

import com.fitmart.analyticsBoard.model.AnalyticsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsDataService analyticsDataService;

    public Map<String, AnalyticsData> getAllAnalyticsData() {
        return analyticsDataService.getAllAnalyticsData();
    }
}