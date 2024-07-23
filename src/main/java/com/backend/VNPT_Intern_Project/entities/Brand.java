package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Brand {
    @Id
    private String uuid_brand;
    private String name;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    // Getters and Setters

    public String getUuid_brand() {
        return uuid_brand;
    }

    public void setUuid_brand(String uuid_brand) {
        this.uuid_brand = uuid_brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public LocalDateTime getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(LocalDateTime updated_date) {
        this.updated_date = updated_date;
    }
}
