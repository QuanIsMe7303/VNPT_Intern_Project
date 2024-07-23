package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTO;
import com.backend.VNPT_Intern_Project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
            List<ProductDTO> product = productService.getProductById(id);
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
            List<ProductDTO> products = List.of();
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
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO newProduct) {
        try {
            int product = productService.createProduct(newProduct);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO newProduct, @PathVariable String id) {
        try {
            int product = productService.updateProduct(newProduct, id);
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            int product = productService.deleteProduct(id);
            return new ResponseEntity<>("Số hàng đã xóa: " + product, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
