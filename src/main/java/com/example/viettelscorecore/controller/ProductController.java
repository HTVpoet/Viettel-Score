package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.dto.request.ProductCreateRequest;
import com.example.viettelscorecore.model.dto.request.ProductSearchRequest;
import com.example.viettelscorecore.model.dto.request.ProductUpdateRequest;
import com.example.viettelscorecore.model.dto.response.ApiResponse;
import com.example.viettelscorecore.model.dto.response.ProductResponse;
import com.example.viettelscorecore.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        return ApiResponse.created(productService.createProduct(request));
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long productId) {
        return ApiResponse.success(productService.getProductById(productId));
    }

    @GetMapping("/sku/{sku}")
    public ApiResponse<ProductResponse> getProductBySku(@PathVariable String sku) {
        return ApiResponse.success(productService.getProductBySku(sku));
    }

    @PostMapping("/search")
    public ApiResponse<List<ProductResponse>> search(@RequestBody ProductSearchRequest request) {
        return ApiResponse.success(productService.search(request));
    }

    @PutMapping("/{productId}")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request) {
        return ApiResponse.success(productService.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.success("Product deleted successfully", null);
    }
}
