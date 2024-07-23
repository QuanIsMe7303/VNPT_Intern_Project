package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Product {
    @Id
    private String uuid_product;
    private String title;
    private String meta_title;
    private String summary;
    private Short type;
    private Double price;
    private Short quantity;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private LocalDateTime published_date;
    private String description;
    private String uuid_brand;

    public String getUuid_product() {
        return uuid_product;
    }

    public void setUuid_product(String uuid_product) {
        this.uuid_product = uuid_product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeta_title() {
        return meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
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

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public LocalDateTime getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(LocalDateTime updated_date) {
        this.updated_date = updated_date;
    }

    public LocalDateTime getPublished_date() {
        return published_date;
    }

    public void setPublished_date(LocalDateTime published_date) {
        this.published_date = published_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid_brand() {
        return uuid_brand;
    }

    public void setUuid_brand(String uuid_brand) {
        this.uuid_brand = uuid_brand;
    }
}
