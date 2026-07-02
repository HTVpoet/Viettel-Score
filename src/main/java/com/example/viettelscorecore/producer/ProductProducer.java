package com.example.viettelscorecore.producer;

import com.example.viettelscorecore.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductProducer {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public void publish(ProductCreatedEvent event) {

        kafkaTemplate.send(
                "product-created",
                event
        );
    }

}