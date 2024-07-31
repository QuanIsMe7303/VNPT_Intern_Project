package com.backend.VNPT_Intern_Project.dtos.cartitemdto;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;
import lombok.Data;

@Data
public class CartItemDTOResponse {
    private String uuid_cart_item;
    private String uuid_cart;
    private ProductDTOResponse product;
    private Double price;
    private Double discount;
    private Short quantity;
    private String content;

    public CartItemDTOResponse() {
    }

    public CartItemDTOResponse(String uuid_cart_item, String uuid_cart, ProductDTOResponse product, Double price, Double discount, Short quantity, String content) {
        this.uuid_cart_item = uuid_cart_item;
        this.uuid_cart = uuid_cart;
        this.product = product;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.content = content;
    }

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

    public ProductDTOResponse getProduct() {
        return product;
    }

    public void setProduct(ProductDTOResponse product) {
        this.product = product;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

