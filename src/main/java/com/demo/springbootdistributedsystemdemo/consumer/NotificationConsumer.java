package com.demo.springbootdistributedsystemdemo.consumer;

import com.demo.springbootdistributedsystemdemo.config.KafkaTopicConfig;
import com.demo.springbootdistributedsystemdemo.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = KafkaTopicConfig.PRODUCT_CREATED, groupId = "notification-group")
    public void consume(ProductCreatedEvent productCreatedEvent) {
        log.info("Received product event:{}", productCreatedEvent);
    }
}
