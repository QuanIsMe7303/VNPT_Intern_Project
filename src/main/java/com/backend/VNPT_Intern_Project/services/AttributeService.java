package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTORequest;
import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTOResponse;
import com.backend.VNPT_Intern_Project.entities.Attribute;
import com.backend.VNPT_Intern_Project.entities.Product;
import com.backend.VNPT_Intern_Project.entities.ProductAttribute;
import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
import com.backend.VNPT_Intern_Project.repositories.AttributeRepository;
import com.backend.VNPT_Intern_Project.repositories.ProductAttributeRepository;
import com.backend.VNPT_Intern_Project.repositories.ProductRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IAttributeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AttributeService implements IAttributeInterface {
    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Override
    public AttributeDTOResponse addAttributeToProduct(AttributeDTORequest attributeRequest) {
        Product product = productRepository.findById(attributeRequest.getUuid_product())
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + attributeRequest.getUuid_product()));

        Attribute existingAttribute = attributeRepository.findByAttributeKey(attributeRequest.getKey());

        // Nếu thuộc tính chưa tồn tại, tạo mới thuộc tính
        if (existingAttribute == null) {
            Attribute newAttribute = new Attribute();
            newAttribute.setAttributeKey(attributeRequest.getKey());
            existingAttribute = attributeRepository.save(newAttribute);
        }

        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setUuidProduct(product.getUuidProduct());
        productAttribute.setUuidAttribute(existingAttribute.getUuidAttribute());
        productAttribute.setValue(attributeRequest.getValue());

        productAttributeRepository.save(productAttribute);

        AttributeDTOResponse response = new AttributeDTOResponse();

        response.setUuidProductAttribute(productAttribute.getUuidProductAttribute());
        response.setUuid_attribute(existingAttribute.getUuidAttribute());
        response.setUuid_product(product.getUuidProduct());
        response.setProductName(product.getTitle());
        response.setKey(existingAttribute.getAttributeKey());
        response.setValue(attributeRequest.getValue());

        return response;
    }

    @Override
    public AttributeDTOResponse updateAttribute(String uuidProductAttribute, AttributeDTORequest attributeRequest) {
        ProductAttribute productAttribute = productAttributeRepository.findById(uuidProductAttribute)
                .orElseThrow(() ->new ResourceNotFoundException("ProductAttribute is not found with id: " + uuidProductAttribute));

        Product product = productRepository.findById(attributeRequest.getUuid_product())
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + attributeRequest.getUuid_product()));

        Attribute existingAttribute = attributeRepository.findByAttributeKey(attributeRequest.getKey());

        if (existingAttribute == null) {
            Attribute newAttribute = new Attribute();
            newAttribute.setAttributeKey(attributeRequest.getKey());
            existingAttribute = attributeRepository.save(newAttribute);
        }


        productAttribute.setUuidProduct(product.getUuidProduct());
        productAttribute.setUuidAttribute(existingAttribute.getUuidAttribute());
        productAttribute.setValue(attributeRequest.getValue());

        productAttributeRepository.save(productAttribute);

        AttributeDTOResponse response = new AttributeDTOResponse();

        response.setUuidProductAttribute(uuidProductAttribute);
        response.setUuid_attribute(existingAttribute.getUuidAttribute());
        response.setUuid_product(product.getUuidProduct());
        response.setProductName(product.getTitle());
        response.setKey(existingAttribute.getAttributeKey());
        response.setValue(attributeRequest.getValue());

        return response;
    }

    @Override
    public AttributeDTOResponse deleteAttribute(String uuidProductAttribute) {
        ProductAttribute productAttribute = productAttributeRepository.findById(uuidProductAttribute)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAttribute is not found with id: " + uuidProductAttribute));

        Product product = productRepository.findById(productAttribute.getUuidProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + productAttribute.getUuidProduct()));

        Attribute attribute = attributeRepository.findById(productAttribute.getUuidAttribute())
                .orElseThrow(() -> new ResourceNotFoundException("Attribute is not found with id: " + productAttribute.getUuidAttribute()));

        productAttributeRepository.delete(productAttribute);

        AttributeDTOResponse response = new AttributeDTOResponse();

        response.setUuidProductAttribute(uuidProductAttribute);
        response.setUuid_product(product.getUuidProduct());
        response.setProductName(product.getTitle());
        response.setKey(attribute.getAttributeKey());
        response.setValue(productAttribute.getValue());

        return response;
    }

}
