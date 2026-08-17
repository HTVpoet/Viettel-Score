package com.example.viettelscorecore.repository;

import com.example.viettelscorecore.model.entity.Product;
import com.example.viettelscorecore.model.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    @Query("""
            SELECT p FROM Product p
            WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                                   OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:category IS NULL OR p.category = :category)
              AND (:brand IS NULL OR p.brand = :brand)
              AND (:status IS NULL OR p.status = :status)
              AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            ORDER BY p.createdAt DESC
            """)
    List<Product> search(
            @Param("keyword") String keyword,
            @Param("category") String category,
            @Param("brand") String brand,
            @Param("status") ProductStatus status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );
}
