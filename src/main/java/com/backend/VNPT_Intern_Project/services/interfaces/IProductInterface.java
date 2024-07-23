package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORsponse;

import java.util.List;

public interface IProductInterface {
    List<ProductDTORsponse> getAllProducts() throws Exception;

    List<ProductDTORsponse> getProductById(String uuid_product) throws Exception;

    List<ProductDTORsponse> getProductsByBrandName(String brand_name) throws Exception;

    List<ProductDTORsponse> getProductsByCategoryName(String category_name) throws Exception;

    List<ProductDTORsponse> getProductsByBrandAndCategory(String brandName, String categoryName) throws Exception;

    List<ProductDTORsponse> createProduct(ProductDTORequest product) throws Exception;

    List<ProductDTORsponse> updateProduct(ProductDTORequest product, String uuid_product) throws Exception;

    List<ProductDTORsponse> deleteProduct(String uuid_product) throws Exception;
}
