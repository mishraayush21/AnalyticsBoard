package com.fitmart.analyticsBoard.repository;

import com.fitmart.analyticsBoard.model.AnalyticsData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsDataRepository extends JpaRepository<AnalyticsData, Long> {
    AnalyticsData findByProductId(String productId);

}
