package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;
import com.backend.VNPT_Intern_Project.repositories.ProductRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductInterface {
    @Autowired
    private ProductRepository productRepository;

    // GET methods
    @Override
    public List<ProductDTOResponse> getAllProducts() throws Exception {
        return productRepository.getAllProducts();
    }

    @Override
    public List<ProductDTOResponse> getProductById(String uuid_product) throws Exception {
        return productRepository.getProductById(uuid_product);
    }

    @Override
    public List<ProductDTOResponse> getProductsByBrandName(String brand_name) throws Exception {
        return productRepository.getProductsByBrandName(brand_name);
    }

    @Override
    public List<ProductDTOResponse> getProductsByCategoryName(String category_name) throws Exception {
        return productRepository.getProductsByCategoryName(category_name);
    }

    @Override
    public List<ProductDTOResponse> getProductsByBrandAndCategory(String brand_name, String category_name) throws Exception {
        return productRepository.getProductsByBrandAndCategory(brand_name, category_name);
    }

    // create
    @Override
    public List<ProductDTOResponse> createProduct(ProductDTORequest product) throws Exception {
        return productRepository.createProduct(product);
    }

    // update
    @Override
    public List<ProductDTOResponse> updateProduct(ProductDTORequest product, String uuid_product) throws Exception {
        return productRepository.updateProduct(product, uuid_product);
    }

    // delete
    @Override
    public List<ProductDTOResponse> deleteProduct(String uuid_product) throws Exception {
        return productRepository.deleteProduct(uuid_product);
    }
}

