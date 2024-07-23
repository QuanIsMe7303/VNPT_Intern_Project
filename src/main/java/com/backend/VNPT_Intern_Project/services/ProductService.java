package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTO;
import com.backend.VNPT_Intern_Project.entities.Product;
import com.backend.VNPT_Intern_Project.repositories.ProductRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductInterface {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts() throws Exception {
        return productRepository.getAllProducts();
    }
//
    @Override
    public List<ProductDTO> getProductById(String uuid_product) throws Exception {
        return productRepository.getProductById(uuid_product);
    }
//
    @Override
    public List<ProductDTO> getProductsByBrandName(String brand_name) throws Exception {
        return productRepository.getProductsByBrandName(brand_name);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryName(String category_name) throws Exception {
        return productRepository.getProductsByCategoryName(category_name);
    }

    @Override
    public List<ProductDTO> getProductsByBrandAndCategory(String brand_name, String category_name) throws Exception {
        return productRepository.getProductsByBrandAndCategory(brand_name, category_name);
    }

    @Override
    public int createProduct(ProductDTO product) throws Exception {
        return productRepository.createProduct(product);
    }

    @Override
    public int updateProduct(ProductDTO product, String uuid_product) throws Exception {
        return productRepository.updateProduct(product, uuid_product);
    }

    @Override
    public int deleteProduct(String uuid_product) throws Exception {
        return productRepository.deleteProduct(uuid_product);
    }

}

