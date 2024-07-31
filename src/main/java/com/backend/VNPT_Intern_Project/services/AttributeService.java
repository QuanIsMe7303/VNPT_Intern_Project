package com.backend.vnptproject.services;

import com.backend.vnptproject.dtos.attributedto.AttributeDTORequest;
import com.backend.vnptproject.dtos.attributedto.AttributeDTOResponse;
import com.backend.vnptproject.entities.Attribute;
import com.backend.vnptproject.entities.Product;
import com.backend.vnptproject.entities.ProductAttribute;
import com.backend.vnptproject.exception.ResourceNotFoundException;
import com.backend.vnptproject.repositories.AttributeRepository;
import com.backend.vnptproject.repositories.ProductAttributeRepository;
import com.backend.vnptproject.repositories.ProductJpaRepository;
import com.backend.vnptproject.services.interfaces.IAttributeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AttributeService implements IAttributeInterface {
    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ProductJpaRepository productRepository;

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
