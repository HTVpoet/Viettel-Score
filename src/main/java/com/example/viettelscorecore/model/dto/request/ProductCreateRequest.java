package com.example.viettelscorecore.model.dto.request;

import com.example.viettelscorecore.model.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateRequest {

    @NotBlank(message = "SKU cannot be blank")
    @Size(max = 64, message = "SKU must be at most 64 characters")
    private String sku;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 200, message = "Name must be at most 200 characters")
    private String name;

    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Size(max = 100, message = "Category must be at most 100 characters")
    private String category;

    @Size(max = 100, message = "Brand must be at most 100 characters")
    private String brand;

    @Size(max = 500, message = "Image URL must be at most 500 characters")
    private String imageUrl;

    @NotNull(message = "Status cannot be null")
    private ProductStatus status;

    @DecimalMin(value = "0.0", message = "Weight cannot be negative")
    private Double weightGrams;

    @Size(max = 100, message = "Dimensions must be at most 100 characters")
    private String dimensions;
}
