package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;

import java.util.List;

public interface IProductInterface {
    List<ProductDTOResponse> getAllProducts() throws Exception;

    List<ProductDTOResponse> getProductById(String uuid_product) throws Exception;

    List<ProductDTOResponse> getProductsByBrandName(String brand_name) throws Exception;

    List<ProductDTOResponse> getProductsByCategoryName(String category_name) throws Exception;

    List<ProductDTOResponse> getProductsByBrandAndCategory(String brandName, String categoryName) throws Exception;

    List<ProductDTOResponse> createProduct(ProductDTORequest product) throws Exception;

    List<ProductDTOResponse> updateProduct(ProductDTORequest product, String uuid_product) throws Exception;

    List<ProductDTOResponse> deleteProduct(String uuid_product) throws Exception;
}
