package com.backend.vnptproject.dtos.attributedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDTOResponse {
    private String uuidProductAttribute;
    private String uuid_attribute;
    private String uuid_product;
    private String productName;
    private String key;
    private String value;
}
