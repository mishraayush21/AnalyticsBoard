package com.fitmart.analyticsBoard.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitmart.analyticsBoard.model.Event;
import com.fitmart.analyticsBoard.model.EventType;
import com.fitmart.analyticsBoard.service.EventProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {

    @Autowired
    EventProcessorService eventProcessorService;

    @KafkaListener(topics = AppConstants.TOPIC_ADDED_TO_CART, groupId = AppConstants.CONSUMER_GROUP_ID)
    public void cartItemsAdded(String message) throws JsonProcessingException {
        processEventFromMessage(message, EventType.ADDED_TO_CART);
    }

    @KafkaListener(topics = AppConstants.TOPIC_PRODUCTS_SOLD, groupId = AppConstants.CONSUMER_GROUP_ID)
    public void itemsSold(String message) throws JsonProcessingException {
        processEventsFromJsonNode(message);
    }

    @KafkaListener(topics = AppConstants.TOPIC_ITEM_VIEWED, groupId = AppConstants.CONSUMER_GROUP_ID)
    public void itemsViewed(String message) throws JsonProcessingException {
        processEventFromMessage(message, EventType.ITEM_VIEWED);
    }

    @KafkaListener(topics = AppConstants.TOPIC_PAYMENT_STATUS, groupId = AppConstants.CONSUMER_GROUP_ID)
    public void paymentStatus(String val) {
        System.out.println("Payment Status\n" + val);
    }

    private void processEventFromMessage(String message, EventType eventType) throws JsonProcessingException {
        String itemId = new ObjectMapper().readTree(message).get("itemId").asText();
        Event event = new Event();
        event.setEventType(eventType);
        event.setProductId(itemId);
        eventProcessorService.processEvent(event);
    }

    private void processEventsFromJsonNode(String message) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(message);
        for (JsonNode node : jsonNode) {
            String itemId = node.get("itemId").asText();
            Event event = new Event();
            event.setEventType(EventType.PRODUCTS_SOLD);
            event.setProductId(itemId);
            eventProcessorService.processEvent(event);
        }
    }
}
