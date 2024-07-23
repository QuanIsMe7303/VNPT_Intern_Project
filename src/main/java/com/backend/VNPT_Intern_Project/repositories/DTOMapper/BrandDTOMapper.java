package com.backend.VNPT_Intern_Project.repositories.DTOMapper;

import com.backend.VNPT_Intern_Project.dtos.BrandDTO.BrandDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BrandDTOMapper implements RowMapper<BrandDTO> {

    private final JdbcTemplate jdbcTemplate;

    public BrandDTOMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BrandDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BrandDTO brand = new BrandDTO();
        brand.setUuidBrand(rs.getString("b.uuid_brand"));
        brand.setName(rs.getString("b.name"));
        brand.setCreatedDate(rs.getObject("b.created_date", LocalDateTime.class));
        brand.setUpdatedDate(rs.getObject("b.updated_date", LocalDateTime.class));
        return brand;
    }
}
