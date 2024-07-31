package com.backend.vnptproject.repositories;

import com.backend.vnptproject.entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, String> {
    List<ProductAttribute> findByUuidProduct(String uuidProduct);
}
