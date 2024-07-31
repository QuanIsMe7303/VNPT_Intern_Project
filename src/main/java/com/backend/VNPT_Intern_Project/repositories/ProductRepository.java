package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByBrandName(String brandName, Pageable pageable);
    Page<Product> findByCategoryTitle(String categoryTitle, Pageable pageable);
    Page<Product> findByBrandNameAndCategoryTitle(String brandName, String categoryName, Pageable pageable);
}
