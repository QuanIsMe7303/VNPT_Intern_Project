package com.backend.VNPT_Intern_Project.dtos.AttributeDTO;


public class AttributeDTOResponse {
    private String uuid_attribute;
    private String uuid_product;
    private String productName;
    private String key;
    private String value;


    public AttributeDTOResponse(String uuid_attribute, String uuid_product, String productName, String key, String value) {
        this.uuid_attribute = uuid_attribute;
        this.uuid_product = uuid_product;
        this.productName = productName;
        this.key = key;
        this.value = value;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
