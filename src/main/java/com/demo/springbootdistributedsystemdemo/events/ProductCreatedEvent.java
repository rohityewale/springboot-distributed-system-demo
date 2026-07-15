package com.demo.springbootdistributedsystemdemo.events;

import java.time.LocalDateTime;

public record ProductCreatedEvent(String eventId, String productId, String productName, double price, String occurredAt) {
}
