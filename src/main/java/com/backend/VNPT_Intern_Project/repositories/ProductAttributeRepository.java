package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, String> {
    List<ProductAttribute> findByUuidProduct(String uuidProduct);
}
