package com.example.viettelscorecore.consumer;

import com.example.viettelscorecore.event.ProductCreatedEvent;
import com.example.viettelscorecore.model.document.ProductDocument;
import com.example.viettelscorecore.repository.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductConsumer {

    private final ProductDocumentRepository repository;

    @KafkaListener(
            topics = "product-created",
            groupId = "product-group")
    public void consume(ProductCreatedEvent event){

        ProductDocument document =
                ProductDocument.builder()
                        .id(event.getId())
                        .name(event.getName())
                        .price(event.getPrice())
                        .build();

        repository.save(document);
        log.info("Consumed event: {}", event);
    }

}