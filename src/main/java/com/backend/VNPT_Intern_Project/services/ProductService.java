package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.product.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.product.ProductDTOResponse;
import com.backend.VNPT_Intern_Project.entities.*;
import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
import com.backend.VNPT_Intern_Project.repositories.AttributeRepository;
import com.backend.VNPT_Intern_Project.repositories.BrandRepository;
import com.backend.VNPT_Intern_Project.repositories.CategoryRepository;
import com.backend.VNPT_Intern_Project.repositories.ProductAttributeRepository;
import com.backend.VNPT_Intern_Project.repositories.ProductRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IProductInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductInterface {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    // GET methods
    @Override
    @Cacheable(value = "allProducts", key = "#pageable.pageNumber")
    public List<ProductDTOResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.stream()
                .map(this::convertToProductDTOResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "products", key = "#uuid_product")
    public ProductDTOResponse getProductById(String uuid_product) {
        Product product = productRepository.findById(uuid_product)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + uuid_product));

        return convertToProductDTOResponse(product);
    }

    @Override
    @Cacheable(value = "productsByBrand", key = "{#brandName, #pageable.pageNumber}")
    public List<ProductDTOResponse> getProductsByBrandName(String brandName, Pageable pageable) {
        Page<Product> products = productRepository.findByBrandName(brandName, pageable);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Products not found for brand: " + brandName);
        }
        return products.stream()
                .map(this::convertToProductDTOResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "productsByCategory", key = "{#categoryTitle, #pageable.pageNumber}")
    public List<ProductDTOResponse> getProductsByCategoryName(String categoryTitle, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryTitle(categoryTitle, pageable);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Products not found for category: " + categoryTitle);
        }
        return products.stream()
                .map(this::convertToProductDTOResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "productsByBrandAndCategory", key = "{#brandName, #categoryTitle, #pageable.pageNumber}")
    public List<ProductDTOResponse> getProductsByBrandAndCategory(String brandName, String categoryTitle, Pageable pageable) {
        Page<Product> products = productRepository.findByBrandNameAndCategoryTitle(brandName, categoryTitle, pageable);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Products not found for brand: " + brandName + " and category: " + categoryTitle);
        }
        return products.stream()
                .map(this::convertToProductDTOResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    @CacheEvict(value = {"products", "allProducts", "productsByBrand", "productsByCategory", "productsByBrandAndCategory"}, allEntries = true)
    public ProductDTOResponse createProduct(ProductDTORequest productRequest) {
        Product product = new Product();

        // Xử lý Brand
        Brand brand = brandRepository.findByName(productRequest.getBrand());
        if (brand == null) {
            brand = new Brand();
            brand.setName(productRequest.getBrand());
            brand = brandRepository.saveAndFlush(brand);
        }
        product.setBrand(brand);

        // Xu ly category
        Category category = categoryRepository.findByTitle(productRequest.getCategory());
        if (category == null) {
            category = new Category();
            category.setTitle(productRequest.getCategory());
            category.setSlug("");
            category = categoryRepository.saveAndFlush(category);
        }
        product.setCategory(category);

        // Product
        product.setTitle(Objects.requireNonNull(productRequest.getTitle(), "Title cannot be null"));
        product.setMetaTitle(productRequest.getMetaTitle());
        product.setSummary(productRequest.getSummary());
        product.setType(productRequest.getType());
        product.setPrice(Objects.requireNonNull(productRequest.getPrice(), "Price cannot be null"));
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());

        productRepository.save(product);

        return convertToProductDTOResponse(product);

    }

    @Transactional
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    @CacheEvict(value = {"products", "allProducts", "productsByBrand", "productsByCategory", "productsByBrandAndCategory"}, allEntries = true)
    public ProductDTOResponse updateProduct(String uuid_product, ProductDTORequest productRequest) {
        Product product = productRepository.findById(uuid_product)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + uuid_product));

        product.setTitle(productRequest.getTitle());

        if (productRequest.getMetaTitle() != null) {
            product.setMetaTitle(productRequest.getMetaTitle());
        }
        if (productRequest.getSummary() != null) {
            product.setSummary(productRequest.getSummary());
        }

        product.setType(productRequest.getType());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());

        if (productRequest.getPublishedDate() != null) {
            product.setPublishedDate(productRequest.getPublishedDate());
        }
        if (productRequest.getDescription() != null) {
            product.setDescription(productRequest.getDescription());
        }

        Brand brand = brandRepository.findByName(productRequest.getBrand());
        if (brand == null) {
            Brand newBrand = new Brand();
            newBrand.setName(productRequest.getBrand());
            brandRepository.saveAndFlush(newBrand);
            product.setBrand(newBrand);
        } else {
            product.setBrand(brand);
        }

        Category category = categoryRepository.findByTitle(productRequest.getCategory());
        if (category == null) {
            Category newCategory = new Category();
            newCategory.setTitle(productRequest.getCategory());
            newCategory.setSlug("");
            categoryRepository.saveAndFlush(newCategory);
            product.setCategory(newCategory);
        } else {
            product.setCategory(category);
        }

        productRepository.save(product);
        return convertToProductDTOResponse(product);
    }

    @Transactional
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @CacheEvict(value = {"products", "allProducts", "productsByBrand", "productsByCategory", "productsByBrandAndCategory"}, allEntries = true)
    public ProductDTOResponse deleteProduct(String uuid_product) {
        Product product = productRepository.findById(uuid_product)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + uuid_product));
        productRepository.delete(product);
        return convertToProductDTOResponse(product);
    }

    private ProductDTOResponse convertToProductDTOResponse(Product product) {
        ProductDTOResponse productDTOResponse = new ProductDTOResponse();

        productDTOResponse.setUuidProduct(product.getUuidProduct());
        productDTOResponse.setTitle(product.getTitle());
        productDTOResponse.setMetaTitle(product.getMetaTitle());
        productDTOResponse.setSummary(product.getSummary());
        productDTOResponse.setType(product.getType());
        productDTOResponse.setPrice(product.getPrice());
        productDTOResponse.setQuantity(product.getQuantity());
        productDTOResponse.setCreatedDate(product.getCreatedDate());
        productDTOResponse.setUpdatedDate(product.getUpdatedDate());
        productDTOResponse.setPublishedDate(product.getPublishedDate());
        productDTOResponse.setDescription(product.getDescription());

        // Set Brand
        if (product.getBrand() != null) {
            ProductDTOResponse.BrandProductResponse brandResponse = new ProductDTOResponse.BrandProductResponse();
            brandResponse.setUuidBrand(product.getBrand().getUuidBrand());
            brandResponse.setName(product.getBrand().getName());
            productDTOResponse.setBrand(brandResponse);
        }

        // Set Category
        if (product.getCategory() != null) {
            ProductDTOResponse.CategoryProductResponse categoryResponse = new ProductDTOResponse.CategoryProductResponse();
            categoryResponse.setUuidCategory(product.getCategory().getUuidCategory());
            categoryResponse.setTitle(product.getCategory().getTitle());
            productDTOResponse.setCategory(categoryResponse);
        }

        // Set otherAttributes
        List<ProductAttribute> attributes = productAttributeRepository.findByUuidProduct(product.getUuidProduct());

        Map<String, List<String>> groupedAttributes = attributes.stream()
                .collect(Collectors.groupingBy(
                        pa -> {
                            // Find the attribute key from the Attribute repository
                            return attributeRepository.findById(pa.getUuidAttribute())
                                    .map(Attribute::getAttributeKey)
                                    .orElse("Unknown");
                        },
                        Collectors.mapping(ProductAttribute::getValue, Collectors.toList())
                ));

        // Convert map to list of AttributeDTO
        List<ProductDTOResponse.AttributeProductResponse> otherAttributes = groupedAttributes.entrySet().stream()
                .map(entry -> {
                    ProductDTOResponse.AttributeProductResponse attributeDTO = new ProductDTOResponse.AttributeProductResponse();
                    attributeDTO.setKey(entry.getKey());
                    attributeDTO.setValues(entry.getValue());
                    return attributeDTO;
                })
                .collect(Collectors.toList());

        productDTOResponse.setOtherAttributes(otherAttributes);

        return productDTOResponse;
    }
}
