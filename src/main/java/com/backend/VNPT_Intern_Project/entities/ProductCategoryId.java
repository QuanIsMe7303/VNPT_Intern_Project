package com.backend.VNPT_Intern_Project.entities;

import java.io.Serializable;
import java.util.Objects;

public class ProductCategoryId implements Serializable {

    private String uuidProduct;
    private String uuidCategory;

    public ProductCategoryId() {}

    public ProductCategoryId(String uuidProduct, String uuidCategory) {
        this.uuidProduct = uuidProduct;
        this.uuidCategory = uuidCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryId that = (ProductCategoryId) o;
        return uuidProduct.equals(that.uuidProduct) && uuidCategory.equals(that.uuidCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidProduct, uuidCategory);
    }
}
