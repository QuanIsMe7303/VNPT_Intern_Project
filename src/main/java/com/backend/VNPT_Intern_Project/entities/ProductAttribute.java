package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.*;

@Entity
@IdClass(ProductAttributeId.class)
public class ProductAttribute {
    @Id
    private String uuidProduct;
    @Id
    private String uuidAttribute;

    private String value;

    // Getters and Setters

    public String getUuidProduct() {
        return uuidProduct;
    }

    public void setUuidProduct(String uuidProduct) {
        this.uuidProduct = uuidProduct;
    }

    public String getUuidAttribute() {
        return uuidAttribute;
    }

    public void setUuidAttribute(String uuidAttribute) {
        this.uuidAttribute = uuidAttribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
