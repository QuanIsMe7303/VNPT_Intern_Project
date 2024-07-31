package com.backend.vnptproject.repositories;

import com.backend.vnptproject.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Brand findByName(String brandName);
}
