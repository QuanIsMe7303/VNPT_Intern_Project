package com.backend.VNPT_Intern_Project.dtos.product;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTOResponse implements Serializable {
    private String uuidProduct;
    private String title;
    private String metaTitle;
    private String summary;
    private Integer type;
    private Double price;
    private Integer quantity;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime publishedDate;
    private String description;
    private BrandProductResponse brand;
    private CategoryProductResponse category;
    private List<AttributeProductResponse> otherAttributes;

    @Data
    public static class BrandProductResponse implements Serializable{
        private String uuidBrand;
        private String name;
    }

    @Data
    public static class CategoryProductResponse implements Serializable{
        private String uuidCategory;
        private String title;
    }

    @Data
    public static class AttributeProductResponse implements Serializable{
        private String key;
        private List<String> values;
    }
}
