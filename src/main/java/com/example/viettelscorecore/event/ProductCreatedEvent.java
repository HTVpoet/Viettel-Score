package com.example.viettelscorecore.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreatedEvent {

    private Long id;

    private String name;

    private BigDecimal price;

}