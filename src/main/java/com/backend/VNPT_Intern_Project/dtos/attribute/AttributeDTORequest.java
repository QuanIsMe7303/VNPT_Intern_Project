package com.backend.VNPT_Intern_Project.dtos.attribute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttributeDTORequest {
    private String uuid_attribute;

    @NotNull(message = "is required")
    private String uuid_product;

    @NotBlank(message = "is required")
    private String key;

    @NotBlank(message = "is required")
    private String value;
}
