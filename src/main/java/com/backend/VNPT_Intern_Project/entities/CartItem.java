package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class CartItem {
    @Id
    private String uuid_cart_item;
    private String uuid_cart;
    private String uuid_product;
    private Double price;
    private Double discount;
    private Short quantity;
    private Short active;
    private String content;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public String getUuid_cart_item() {
        return uuid_cart_item;
    }

    public void setUuid_cart_item(String uuid_cart_item) {
        this.uuid_cart_item = uuid_cart_item;
    }

    public String getUuid_cart() {
        return uuid_cart;
    }

    public void setUuid_cart(String uuid_cart) {
        this.uuid_cart = uuid_cart;
    }

    public String getUuid_product() {
        return uuid_product;
    }

    public void setUuid_product(String uuid_product) {
        this.uuid_product = uuid_product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
