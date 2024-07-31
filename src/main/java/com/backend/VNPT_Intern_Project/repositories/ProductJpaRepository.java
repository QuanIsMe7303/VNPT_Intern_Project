package com.backend.vnptproject.repositories;

import com.backend.vnptproject.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, String> {
    Page<Product> findByBrandName(String brandName, Pageable pageable);
    Page<Product> findByCategoryTitle(String categoryTitle, Pageable pageable);
    Page<Product> findByBrandNameAndCategoryTitle(String brandName, String categoryName, Pageable pageable);
}
