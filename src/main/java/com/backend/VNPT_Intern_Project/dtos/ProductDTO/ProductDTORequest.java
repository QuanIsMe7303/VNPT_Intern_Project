package com.backend.vnptproject.dtos.productdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTORequest {
    @NotNull(message = "Title is required")
    private String title;

    private String metaTitle;

    private String summary;

    @NotNull(message = "Type is required")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Integer type;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Integer quantity;

    private LocalDateTime publishedDate;

    private String description;

    @NotNull(message = "is required")
    private String brand;

    @NotNull(message = "is required")
    private String category;
}
