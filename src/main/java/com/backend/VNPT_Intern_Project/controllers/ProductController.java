package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORsponse;
import com.backend.VNPT_Intern_Project.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            List<ProductDTORsponse> product = productService.getProductById(id);
            if (product.isEmpty()) {
                return new ResponseEntity<>("Không tồn tại sản phẩm", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(product, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category) {
        try {
            List<ProductDTORsponse> products = List.of();
            if (brand != null && category != null) {
                products = productService.getProductsByBrandAndCategory(brand, category);
            } else if (brand != null) {
                products = productService.getProductsByBrandName(brand);
            } else if (category != null) {
                products = productService.getProductsByCategoryName(category);
            } else {
                products = productService.getAllProducts();
            }

            if (products.isEmpty()) {
                return new ResponseEntity<>("Không tồn tại sản phẩm", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTORequest newProduct) {
        try {
            List<ProductDTORsponse> product = productService.createProduct(newProduct);
            return new ResponseEntity<>(product, new HttpHeaders(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTORequest newProduct, @PathVariable String id) {
        try {
            List<ProductDTORsponse> product = productService.updateProduct(newProduct, id);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            List<ProductDTORsponse> product = productService.deleteProduct(id);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
