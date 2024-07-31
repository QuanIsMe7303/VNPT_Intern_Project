package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.product.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.product.ProductDTOResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductInterface {
    List<ProductDTOResponse> getAllProducts(Pageable pageable);

    ProductDTOResponse getProductById(String uuid_product);

    List<ProductDTOResponse> getProductsByBrandName(String brandName, Pageable pageable);

    List<ProductDTOResponse> getProductsByCategoryName(String categoryTitle, Pageable pageable);

    List<ProductDTOResponse> getProductsByBrandAndCategory(String brandName, String categoryTitle, Pageable pageable);

    ProductDTOResponse createProduct(ProductDTORequest product);

    ProductDTOResponse updateProduct( String uuid_product, ProductDTORequest product);

    ProductDTOResponse deleteProduct(String uuid_product);
}
