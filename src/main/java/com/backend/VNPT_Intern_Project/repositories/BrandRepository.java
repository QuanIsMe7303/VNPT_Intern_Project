package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Brand findByName(String brandName);
}
