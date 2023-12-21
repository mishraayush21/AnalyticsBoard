package com.fitmart.analyticsBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BulkInsertionService {

    @Autowired
    private AnalyticsDataService analyticsDataService;
    @Scheduled(cron = "0 */10 * * * *")
    public void bulkInsert() {
        analyticsDataService.bulkInsertToMySQL();
    }
}
