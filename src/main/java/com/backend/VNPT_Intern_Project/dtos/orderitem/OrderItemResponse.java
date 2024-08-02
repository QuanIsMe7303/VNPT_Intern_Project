package com.backend.VNPT_Intern_Project.dtos.orderitem;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderItemResponse {
    @NotNull
    private String uuidProduct;
    private Double price;
    private Double discount;
    private Integer quantity;
    private String content;
}
