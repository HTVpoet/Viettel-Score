package com.example.viettelscorecore.model.entity;

import com.example.viettelscorecore.model.enums.ProductStatus;
import com.example.viettelscorecore.security.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "products")
@Entity
@Getter
@Setter
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE products SET deleted = true, deleted_at = ?, deleted_by = ? WHERE id = ?")
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku", nullable = false, unique = true, length = 64)
    private String sku;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private ProductStatus status;

    @Column(name = "weight_grams")
    private Double weightGrams;

    @Column(name = "dimensions", length = 100)
    private String dimensions;

    @PreRemove
    private void preRemove() {
        setDeleted(true);
        setDeletedAt(Instant.now());
        setDeletedBy(SecurityUtils.currentUsername() != null ? SecurityUtils.currentUsername() : "system");
    }
}
