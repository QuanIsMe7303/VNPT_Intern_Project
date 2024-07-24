package com.backend.VNPT_Intern_Project.repositories.DTOMapper;

import com.backend.VNPT_Intern_Project.dtos.BrandDTO.BrandDTOResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BrandDTOMapper implements RowMapper<BrandDTOResponse> {

    private final JdbcTemplate jdbcTemplate;

    public BrandDTOMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BrandDTOResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        BrandDTOResponse brand = new BrandDTOResponse();
        brand.setUuidBrand(rs.getString("b.uuid_brand"));
        brand.setName(rs.getString("b.name"));
        brand.setCreatedDate(rs.getObject("b.created_date", LocalDateTime.class));
        brand.setUpdatedDate(rs.getObject("b.updated_date", LocalDateTime.class));
        return brand;
    }
}
