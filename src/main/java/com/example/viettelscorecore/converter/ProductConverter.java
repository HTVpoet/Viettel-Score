package com.example.viettelscorecore.converter;

import com.example.viettelscorecore.model.dto.request.ProductCreateRequest;
import com.example.viettelscorecore.model.dto.request.ProductUpdateRequest;
import com.example.viettelscorecore.model.dto.response.ProductResponse;
import com.example.viettelscorecore.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .brand(product.getBrand())
                .imageUrl(product.getImageUrl())
                .status(product.getStatus())
                .weightGrams(product.getWeightGrams())
                .dimensions(product.getDimensions())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .createdBy(product.getCreatedBy())
                .updatedBy(product.getUpdatedBy())
                .build();
    }

    public Product toEntity(ProductCreateRequest request) {
        Product product = new Product();
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setImageUrl(request.getImageUrl());
        product.setStatus(request.getStatus());
        product.setWeightGrams(request.getWeightGrams());
        product.setDimensions(request.getDimensions());
        return product;
    }

    public void updateEntity(Product product, ProductUpdateRequest request) {
        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getQuantity() != null) product.setQuantity(request.getQuantity());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
        if (request.getBrand() != null) product.setBrand(request.getBrand());
        if (request.getImageUrl() != null) product.setImageUrl(request.getImageUrl());
        if (request.getStatus() != null) product.setStatus(request.getStatus());
        if (request.getWeightGrams() != null) product.setWeightGrams(request.getWeightGrams());
        if (request.getDimensions() != null) product.setDimensions(request.getDimensions());
    }
}
