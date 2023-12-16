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
        switch (event.getEventType()) {
            case ITEM_VIEWED:
                analyticsDataService.incrementViews(event.getProductId(), eventCount);
                break;
            case ADDED_TO_CART:
                analyticsDataService.incrementCLickCount(event.getProductId(), eventCount);
                break;
            case PRODUCTS_SOLD:
                analyticsDataService.incrementSaleCount(event.getProductId(), eventCount);
                break;
            default:
                break;
        }
    }
}
