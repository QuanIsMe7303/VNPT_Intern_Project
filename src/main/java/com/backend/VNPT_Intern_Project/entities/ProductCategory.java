package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_category")
@IdClass(ProductCategoryId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductCategory {
    @Id
    @Column(name = "uuid_product")
    private String uuidProduct;

    @Id
    @Column(name = "uuid_category")
    private String uuidCategory;

    @ManyToOne
    @JoinColumn(name = "uuid_product", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "uuid_category", insertable = false, updatable = false)
    private Category category;
}

