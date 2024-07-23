package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.*;

@Entity
@IdClass(ProductCategoryId.class)
public class ProductCategory {
    @Id
    private String uuid_product;
    @Id
    private String uuid_category;

    // Getters and Setters

    public String getUuid_product() {
        return uuid_product;
    }

    public void setUuid_product(String uuid_product) {
        this.uuid_product = uuid_product;
    }

    public String getUuid_category() {
        return uuid_category;
    }

    public void setUuid_category(String uuid_category) {
        this.uuid_category = uuid_category;
    }
}
