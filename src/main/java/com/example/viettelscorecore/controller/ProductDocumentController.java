package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.document.ProductDocument;
import com.example.viettelscorecore.service.ProductDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search/products")
@RequiredArgsConstructor
public class ProductDocumentController {

    private final ProductDocumentService productDocumentService;

    @PostMapping
    public ProductDocument save(@RequestBody ProductDocument product) {
        return productDocumentService.save(product);
    }

    @PostMapping("/bulk")
    public Iterable<ProductDocument> saveAll(
            @RequestBody List<ProductDocument> products) {

        return productDocumentService.saveAll(products);
    }

    @GetMapping
    public Iterable<ProductDocument> findAll() {
        return productDocumentService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDocument findById(@PathVariable Long id) {
        return productDocumentService.findById(id);
    }

    @GetMapping("/name")
    public List<ProductDocument> searchByName(
            @RequestParam String keyword) {

        return productDocumentService.searchByName(keyword);
    }

    @GetMapping("/brand")
    public List<ProductDocument> searchByBrand(
            @RequestParam String brand) {

        return productDocumentService.searchByBrand(brand);
    }

    @GetMapping("/price")
    public List<ProductDocument> searchByPrice(
            @RequestParam Double min,
            @RequestParam Double max) {

        return productDocumentService.searchByPrice(min, max);
    }

    @GetMapping("/available")
    public List<ProductDocument> available(
            @RequestParam Boolean value) {

        return productDocumentService.searchAvailable(value);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productDocumentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}