package com.example.viettelscorecore.model.entity;

import com.example.viettelscorecore.security.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;

@Table(name = "users")
@Entity
@Getter
@Setter
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true, deleted_at = ?, deleted_by = ? WHERE id = ?")
@NoArgsConstructor
public class Users extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @PreRemove
    private void preRemove() {
        setDeleted(true);
        setDeletedAt(Instant.now());
        setDeletedBy(SecurityUtils.currentUsername() != null ? SecurityUtils.currentUsername() : "system");
    }
}
