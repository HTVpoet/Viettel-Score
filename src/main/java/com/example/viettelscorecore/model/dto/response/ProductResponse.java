package com.example.viettelscorecore.model.dto.response;

import com.example.viettelscorecore.model.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private String brand;
    private String imageUrl;
    private ProductStatus status;
    private Double weightGrams;
    private String dimensions;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
