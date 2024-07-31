package com.backend.vnptproject.controllers;

import com.backend.vnptproject.dtos.ApiResponse;
import com.backend.vnptproject.dtos.AttributeDTO.AttributeDTORequest;
import com.backend.vnptproject.dtos.AttributeDTO.AttributeDTOResponse;
import com.backend.vnptproject.dtos.ProductDTO.ProductDTORequest;
import com.backend.vnptproject.dtos.ProductDTO.ProductDTOResponse;
import com.backend.vnptproject.exception.ApiRequestException;
import com.backend.vnptproject.services.AttributeService;
import com.backend.vnptproject.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AttributeService attributeService;

    @GetMapping("")
    public ResponseEntity<?> getProducts(@RequestParam(required = false) String brand,
                                         @RequestParam(required = false) String category,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "title") String sortBy) {
        try {
            PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
            List<ProductDTOResponse> products;
            if (brand != null && category != null) {
                products = productService.getProductsByBrandAndCategory(brand, category, pageable);
            } else if (brand != null) {
                products = productService.getProductsByBrandName(brand, pageable);
            } else if (category != null) {
                products = productService.getProductsByCategoryName(category, pageable);
            } else {
                products = productService.getAllProducts(pageable);
            }
            ApiResponse<List<ProductDTOResponse>> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException("Error while fetching product: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        ProductDTOResponse product = productService.getProductById(id);
        ApiResponse<ProductDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTORequest newProduct) {
        ProductDTOResponse product = productService.createProduct(newProduct);
        ApiResponse<ProductDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTORequest newProduct, @PathVariable String id) {
        ProductDTOResponse product = productService.updateProduct(id, newProduct);
        ApiResponse<ProductDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        ApiResponse<ProductDTOResponse> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "SUCCESS", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/attribute")
    public ResponseEntity<?> addAttributeToProduct(@Valid @RequestBody AttributeDTORequest attribute) {
        AttributeDTOResponse newAttribute = attributeService.addAttributeToProduct(attribute);
        ApiResponse<AttributeDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", newAttribute);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/attribute/{uuidProductAttribute}")
    public ResponseEntity<?> updateAttribute(@PathVariable String uuidProductAttribute, @Valid @RequestBody AttributeDTORequest attribute) {
        AttributeDTOResponse product = attributeService.updateAttribute(uuidProductAttribute, attribute);
        ApiResponse<AttributeDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/attribute/{uuidProductAttribute}")
    public ResponseEntity<?> deleteAttribute(@PathVariable String uuidProductAttribute) {
        AttributeDTOResponse attribute = attributeService.deleteAttribute(uuidProductAttribute);
        ApiResponse<AttributeDTOResponse> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "SUCCESS", attribute);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}












