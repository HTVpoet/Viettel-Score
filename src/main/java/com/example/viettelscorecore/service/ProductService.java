package com.example.viettelscorecore.service;

import com.example.viettelscorecore.model.dto.request.ProductCreateRequest;
import com.example.viettelscorecore.model.dto.request.ProductSearchRequest;
import com.example.viettelscorecore.model.dto.request.ProductUpdateRequest;
import com.example.viettelscorecore.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    ProductResponse getProductById(Long productId);
    ProductResponse getProductBySku(String sku);
    List<ProductResponse> search(ProductSearchRequest request);
    ProductResponse updateProduct(Long productId, ProductUpdateRequest request);
    void deleteProduct(Long productId);
}
