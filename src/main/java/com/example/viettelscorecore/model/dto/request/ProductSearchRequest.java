package com.example.viettelscorecore.model.dto.request;

import com.example.viettelscorecore.model.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSearchRequest {
    private String keyword;
    private String category;
    private String brand;
    private ProductStatus status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
