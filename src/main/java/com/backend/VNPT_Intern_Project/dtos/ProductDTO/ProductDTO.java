package com.backend.VNPT_Intern_Project.dtos.ProductDTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;

public class ProductDTO {
    @NotNull
    private String uuidProduct;

    @NotNull
    private String title;

    private String metaTitle;

    private String summary;

    @NotNull
    private Short type;

    @NotNull
    private Double price;

    @NotNull
    private Short quantity;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime publishedDate;

    private String description;

    @NotNull
    private String brand;

    private String category;

    private Map<String, String> otherAttributes;

    // Getters and Setters

    public String getUuidProduct() {
        return uuidProduct;
    }

    public void setUuidProduct(String uuidProduct) {
        this.uuidProduct = uuidProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, String> getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(Map<String, String> otherAttributes) {
        this.otherAttributes = otherAttributes;
    }
}
