package com.backend.vnptproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_product", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidProduct;

    @NotBlank
    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "summary")
    private String summary;

    @Column(name = "type")
    private Integer type;

    @NotNull
    @Min(0)
    @Column(name = "price")
    private Double price;

    @Min(0)
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "uuid_brand", referencedColumnName = "uuid_brand")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "uuid_category")
    @JsonIgnoreProperties({"productList"})
    private Category category;
}
