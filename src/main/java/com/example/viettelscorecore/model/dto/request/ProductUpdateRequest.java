package com.example.viettelscorecore.model.dto.request;

import com.example.viettelscorecore.model.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateRequest {

    @Size(max = 200, message = "Name must be at most 200 characters")
    private String name;

    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Size(max = 100, message = "Category must be at most 100 characters")
    private String category;

    @Size(max = 100, message = "Brand must be at most 100 characters")
    private String brand;

    @Size(max = 500, message = "Image URL must be at most 500 characters")
    private String imageUrl;

    private ProductStatus status;

    @DecimalMin(value = "0.0", message = "Weight cannot be negative")
    private Double weightGrams;

    @Size(max = 100, message = "Dimensions must be at most 100 characters")
    private String dimensions;
}
