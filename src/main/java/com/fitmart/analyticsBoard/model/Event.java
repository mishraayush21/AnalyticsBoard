package com.fitmart.analyticsBoard.model;

public class Event {

    private String productId;
    private EventType eventType;

    public Event() {
    }

    public Event(String productId, EventType eventType) {
        this.productId = productId;
        this.eventType = eventType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
