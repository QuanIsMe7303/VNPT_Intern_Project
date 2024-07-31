package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByTitle(String title);
}
