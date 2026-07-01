package com.example.viettelscorecore.service;

import com.example.viettelscorecore.model.document.ProductDocument;
import com.example.viettelscorecore.repository.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDocumentService {

    private final ProductDocumentRepository repository;

    public ProductDocument save(ProductDocument document) {
        return repository.save(document);
    }

    public List<ProductDocument> search(String keyword) {
        return repository.findByNameContaining(keyword);
    }

    public Iterable<ProductDocument> saveAll(List<ProductDocument> products) {
        return repository.saveAll(products);
    }

    public Iterable<ProductDocument> findAll() {
        return repository.findAll();
    }

    public ProductDocument findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ProductDocument> searchByName(String keyword) {
        return repository.findByNameContaining(keyword);
    }

    public List<ProductDocument> searchByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    public List<ProductDocument> searchByPrice(Double min, Double max) {
        return repository.findByPriceBetween(min, max);
    }

    public List<ProductDocument> searchAvailable(Boolean available) {
        return repository.findByAvailable(available);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}