package com.example.viettelscorecore.service.impl;

import com.example.viettelscorecore.converter.ProductConverter;
import com.example.viettelscorecore.model.dto.request.ProductCreateRequest;
import com.example.viettelscorecore.model.dto.request.ProductSearchRequest;
import com.example.viettelscorecore.model.dto.request.ProductUpdateRequest;
import com.example.viettelscorecore.model.dto.response.ProductResponse;
import com.example.viettelscorecore.model.entity.Product;
import com.example.viettelscorecore.repository.ProductRepository;
import com.example.viettelscorecore.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        log.info("Create product with SKU: {}", request.getSku());
        if (productRepository.existsBySku(request.getSku())) {
            throw new IllegalArgumentException("Product with SKU '" + request.getSku() + "' already exists");
        }
        Product product = productConverter.toEntity(request);
        Product saved = productRepository.save(product);
        return productConverter.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        log.info("Get product by id: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return productConverter.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductBySku(String sku) {
        log.info("Get product by SKU: {}", sku);
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found with SKU: " + sku));
        return productConverter.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> search(ProductSearchRequest request) {
        log.info("Search products with filters: {}", request);
        return productRepository.search(
                        request.getKeyword(),
                        request.getCategory(),
                        request.getBrand(),
                        request.getStatus(),
                        request.getMinPrice(),
                        request.getMaxPrice())
                .stream()
                .map(productConverter::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        log.info("Update product with id: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productConverter.updateEntity(product, request);
        Product updated = productRepository.save(product);
        return productConverter.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        log.info("Delete product with id: {}", productId);
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }
}
