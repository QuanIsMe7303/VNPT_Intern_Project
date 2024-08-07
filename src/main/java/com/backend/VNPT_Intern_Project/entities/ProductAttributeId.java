package com.backend.VNPT_Intern_Project.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ProductAttributeId implements Serializable {
    private String uuidProduct;
    private String uuidAttribute;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttributeId that = (ProductAttributeId) o;
        return Objects.equals(uuidProduct, that.uuidProduct) && Objects.equals(uuidAttribute, that.uuidAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidProduct, uuidAttribute);
    }
}
