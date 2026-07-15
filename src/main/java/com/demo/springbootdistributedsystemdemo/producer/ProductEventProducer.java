package com.demo.springbootdistributedsystemdemo.producer;


import com.demo.springbootdistributedsystemdemo.config.KafkaTopicConfig;
import com.demo.springbootdistributedsystemdemo.entity.Product;
import com.demo.springbootdistributedsystemdemo.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductEventProducer(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishProductEvents(Product product) {
        UUID id = UUID.randomUUID();
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(id.toString(), product.getId(),
                product.getName(), product.getPrice(), LocalDateTime.now().toString());
        kafkaTemplate.send(KafkaTopicConfig.PRODUCT_CREATED, productCreatedEvent.productId(), productCreatedEvent)
                .whenComplete(((result, ex) -> {
                    if (ex != null) {

                    } else {
                        log.info("Product created event sent with partition:{} and offset:{}",
                                result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    }
                }));
    }
}
