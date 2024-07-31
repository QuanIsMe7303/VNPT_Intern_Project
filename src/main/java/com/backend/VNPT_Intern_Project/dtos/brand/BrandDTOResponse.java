package com.backend.VNPT_Intern_Project.dtos.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BrandDTOResponse {
    private String uuidBrand;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
