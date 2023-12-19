package com.fitmart.analyticsBoard.service;

import com.fitmart.analyticsBoard.model.Event;
import com.fitmart.analyticsBoard.model.EventType;
import com.fitmart.analyticsBoard.model.AnalyticsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProcessorService {

    @Autowired
    private AnalyticsDataService analyticsDataService;

    public void processEvent(Event event) {
        int eventCount = 1;
        String productId = event.getProductId();

        switch (event.getEventType()) {
            case ITEM_VIEWED:
                analyticsDataService.incrementCount(productId, eventCount, 0, 0);
                break;
            case ADDED_TO_CART:
                analyticsDataService.incrementCount(productId, 0, eventCount, 0);
                break;
            case PRODUCTS_SOLD:
                analyticsDataService.incrementCount(productId, 0, 0, eventCount);
                break;
            default:
                break;
        }
    }
}
