package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTORequest;
import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;

import java.util.List;

public interface IAttributeInterface {
    AttributeDTOResponse addAttributeToProduct(AttributeDTORequest attribute) throws Exception;

    AttributeDTOResponse updateAttribute(AttributeDTORequest attributeDTORequest) throws Exception;

    AttributeDTOResponse deleteAttribute(String uuid_attribute, String uuid_product) throws Exception;
}
