package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTORequest;
import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;
import com.backend.VNPT_Intern_Project.repositories.AttributeRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IAttributeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService implements IAttributeInterface {
    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public AttributeDTOResponse addAttributeToProduct(AttributeDTORequest attribute) throws Exception {
        return attributeRepository.addAttributeToProduct(attribute);
    }

    @Override
    public AttributeDTOResponse updateAttribute(AttributeDTORequest attributeDTORequest) throws Exception {
        return attributeRepository.updateAttribute(attributeDTORequest);
    }

    @Override
    public AttributeDTOResponse deleteAttribute(String uuid_attribute, String uuid_product) throws Exception{
        return attributeRepository.deleteAttribute(uuid_attribute, uuid_product);
    }

}
