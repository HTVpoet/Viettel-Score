package com.example.viettelscorecore.repository;

import com.example.viettelscorecore.model.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDocumentRepository
        extends ElasticsearchRepository<ProductDocument, Long> {

    List<ProductDocument> findByNameContaining(String keyword);

    List<ProductDocument> findByBrand(String brand);

    List<ProductDocument> findByPriceBetween(Double min, Double max);

    List<ProductDocument> findByAvailable(Boolean available);

}