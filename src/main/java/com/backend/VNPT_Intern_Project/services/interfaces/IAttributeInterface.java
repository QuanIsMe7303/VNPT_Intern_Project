package com.backend.vnptproject.services.interfaces;

import com.backend.vnptproject.dtos.attributedto.AttributeDTORequest;
import com.backend.vnptproject.dtos.attributedto.AttributeDTOResponse;

public interface IAttributeInterface {
    AttributeDTOResponse addAttributeToProduct(AttributeDTORequest attribute);

    AttributeDTOResponse updateAttribute(String uuidProductAttribute, AttributeDTORequest attributeRequest);

    AttributeDTOResponse deleteAttribute(String uuidProductAttribute);
}
