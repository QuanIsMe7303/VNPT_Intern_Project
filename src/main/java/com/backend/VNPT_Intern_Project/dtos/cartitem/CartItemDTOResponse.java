package com.backend.VNPT_Intern_Project.dtos.cartitem;

import com.backend.VNPT_Intern_Project.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItemDTOResponse {
    private String uuidCartItem;
    private String uuidUser;
    private ProductCartItemResponse product;
    private Double price;
    private Double discount;
    private Integer quantity;
    private Integer active;
    private String content;

    @Data
    public static class ProductCartItemResponse {
        private String uuidProduct;
        private String productName;
        private Double price;
        private Integer quantityInStock;
    }
}

