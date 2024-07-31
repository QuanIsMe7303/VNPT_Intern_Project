package com.backend.vnptproject.services.interfaces;

import com.backend.vnptproject.dtos.productdto.ProductDTORequest;
import com.backend.vnptproject.dtos.productdto.ProductDTOResponse;
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

    void deleteProduct(String uuid_product);
}
