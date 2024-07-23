package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTO;
import com.backend.VNPT_Intern_Project.entities.Product;
import java.util.List;
import java.util.Optional;

public interface IProductInterface {
    List<ProductDTO> getAllProducts() throws Exception;

    List<ProductDTO> getProductById(String uuid_product) throws Exception;

    List<ProductDTO> getProductsByBrandName(String brand_name) throws Exception;

    List<ProductDTO> getProductsByCategoryName(String category_name) throws Exception;

    List<ProductDTO> getProductsByBrandAndCategory(String brandName, String categoryName) throws Exception;

    int createProduct(ProductDTO product) throws Exception;

    int updateProduct(ProductDTO product, String uuid_product) throws Exception;

    int deleteProduct(String uuid_product) throws Exception;
}
