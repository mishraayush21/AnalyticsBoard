package com.fitmart.analyticsBoard.service;

import com.fitmart.analyticsBoard.model.Event;
import com.fitmart.analyticsBoard.model.EventType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventCountService {

    private final Map<String, Integer> viewCountMap = new HashMap<>();
    private final Map<String, Integer> cartCountMap = new HashMap<>();
    private final Map<String, Integer> saleCountMap = new HashMap<>();

    public void processEvent(Event event) {
        String productId = event.getProductId();

        switch (event.getEventType()) {
            case ITEM_VIEWED:
                incrementCount(viewCountMap, productId);
                break;
            case ADDED_TO_CART:
                incrementCount(cartCountMap, productId);
                break;
            case PRODUCTS_SOLD:
                incrementCount(saleCountMap, productId);
                break;
        }
    }

    public int getViewCount(String productId) {
        return getCount(viewCountMap, productId);
    }

    public int getCartCount(String productId) {
        return getCount(cartCountMap, productId);
    }

    public int getSaleCount(String productId) {
        return getCount(saleCountMap, productId);
    }

    private void incrementCount(Map<String, Integer> countMap, String key) {
        countMap.put(key, countMap.getOrDefault(key, 0) + 1);
    }

    private int getCount(Map<String, Integer> countMap, String key) {
        return countMap.getOrDefault(key, 0);
    }
}
