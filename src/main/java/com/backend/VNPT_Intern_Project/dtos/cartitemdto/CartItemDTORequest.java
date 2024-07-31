package com.backend.vnptproject.dtos.cartitemdto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemDTORequest {
    private String uuid_cart;

    @NotNull(message = "is required")
    private String uuid_product;

    @NotNull(message = "is required") @Min(1)
    private Short quantity;

    private Double price;

    @Min(0) @Max(1)
    private Double discount;

    private String content;

    // Getters and Setters


    public String getUuid_cart() {
        return uuid_cart;
    }

    public void setUuid_cart(String uuid_cart) {
        this.uuid_cart = uuid_cart;
    }

    public @NotNull String getUuid_product() {
        return uuid_product;
    }

    public void setUuid_product(@NotNull String uuid_product) {
        this.uuid_product = uuid_product;
    }

    public @NotNull Short getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull Short quantity) {
        this.quantity = quantity;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}