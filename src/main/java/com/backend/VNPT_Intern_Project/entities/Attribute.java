package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Attribute {
    @Id
    private String uuidAttribute;

    private String key;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    // Getters and Setters

    public String getUuidAttribute() {
        return uuidAttribute;
    }

    public void setUuidAttribute(String uuidAttribute) {
        this.uuidAttribute = uuidAttribute;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
