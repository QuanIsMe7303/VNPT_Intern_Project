package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.attribute.AttributeDTORequest;
import com.backend.VNPT_Intern_Project.dtos.attribute.AttributeDTOResponse;

public interface IAttributeInterface {
    AttributeDTOResponse addAttributeToProduct(AttributeDTORequest attribute);

    AttributeDTOResponse updateAttribute(String uuidProductAttribute, AttributeDTORequest attributeRequest);

    AttributeDTOResponse deleteAttribute(String uuidProductAttribute);
}
