package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.dtos.BrandDTO.BrandDTOResponse;
import com.backend.VNPT_Intern_Project.repositories.DTOMapper.BrandDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public BrandRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BrandDTOResponse> findAll() {
        String sql = "SELECT * FROM brand b";
        return jdbcTemplate.query(sql, new BrandDTOMapper(jdbcTemplate));
    }
}
