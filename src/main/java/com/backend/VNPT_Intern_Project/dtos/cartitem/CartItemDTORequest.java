package com.backend.VNPT_Intern_Project.dtos.cartitem;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItemDTORequest {

    @NotNull(message = "is required")
    private String uuidProduct;

    @NotNull(message = "is required") @Min(value = 1, message = " is at least 1")
    private Integer quantity;

    @NotNull(message = "is required")
    @Min(0) @Max(1)
    private Double discount;

    @NotNull(message = "is required")
    private Integer active;

    private String content;
}