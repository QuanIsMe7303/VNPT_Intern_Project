package com.backend.VNPT_Intern_Project.dtos.BrandDTO;

import java.time.LocalDateTime;

public class BrandDTOResponse {
    private String uuidBrand;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public String getUuidBrand() {
        return uuidBrand;
    }

    public void setUuidBrand(String uuidBrand) {
        this.uuidBrand = uuidBrand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
