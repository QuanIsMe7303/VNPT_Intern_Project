package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTORequest;
import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;
import com.backend.VNPT_Intern_Project.services.AttributeService;
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

    @Autowired
    private AttributeService attributeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            List<ProductDTOResponse> product = productService.getProductById(id);
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
            List<ProductDTOResponse> products = List.of();
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
            List<ProductDTOResponse> product = productService.createProduct(newProduct);
            return new ResponseEntity<>(product, new HttpHeaders(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTORequest newProduct, @PathVariable String id) {
        try {
            List<ProductDTOResponse> product = productService.updateProduct(newProduct, id);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            List<ProductDTOResponse> product = productService.deleteProduct(id);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/attribute")
    public ResponseEntity<?> addAttributeToProduct(@Valid @RequestBody AttributeDTORequest attribute) {
        try {
            AttributeDTOResponse newAttribute = attributeService.addAttributeToProduct(attribute);
            if (newAttribute != null) {
                return new ResponseEntity<>(newAttribute, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/attribute")
    public ResponseEntity<?> updateAttribute(@Valid @RequestBody AttributeDTORequest attribute) {
        try {
            AttributeDTOResponse product = attributeService.updateAttribute(attribute);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/attribute")
    public ResponseEntity<?> updateAttribute(@RequestParam(required = true) String attributeId,
                                             @RequestParam(required = true) String productId) {
        try {
            AttributeDTOResponse attribute = attributeService.deleteAttribute(attributeId, productId);
            if (attribute != null) {
                return new ResponseEntity<>(attribute, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
