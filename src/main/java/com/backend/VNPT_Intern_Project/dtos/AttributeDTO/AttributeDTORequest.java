package com.backend.VNPT_Intern_Project.dtos.AttributeDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AttributeDTORequest {
    private String uuid_attribute;

    @NotNull
    private String uuid_product;

    @NotBlank
    private String key;

    @NotBlank
    private String value;

    public String getUuid_attribute() {
        return uuid_attribute;
    }

    public void setUuid_attribute(String uuid_attribute) {
        this.uuid_attribute = uuid_attribute;
    }

    public String getUuid_product() {
        return uuid_product;
    }

    public void setUuid_product(String uuid_product) {
        this.uuid_product = uuid_product;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
