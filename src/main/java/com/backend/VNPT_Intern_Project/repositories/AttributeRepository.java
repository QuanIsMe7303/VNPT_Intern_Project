package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTORequest;
import com.backend.VNPT_Intern_Project.dtos.AttributeDTO.AttributeDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AttributeRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductRepository productRepository;

    public AttributeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AttributeDTOResponse addAttributeToProduct(AttributeDTORequest attribute) {
        // Kiểm tra sản phẩm đã tồn tại chưa
        String findProductQuery =
                "SELECT uuid_product FROM product " +
                "WHERE uuid_product = ?";

        String productID = null;
        String productName = null;
        try {
            productID = jdbcTemplate.queryForObject(findProductQuery, new Object[]{attribute.getUuid_product()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Can not find product with id = " + productID);
            return null;
        }

        if (productID != null) {
            String getProductNameQuery = "SELECT `title` FROM product WHERE uuid_product = ?";
            productName = jdbcTemplate.queryForObject(getProductNameQuery, new Object[]{productID}, String.class);
        }

        // Kiểm tra đã tồn tại attribute với key truyền vào chưa
        String findAttributeIdQuery = "SELECT uuid_attribute FROM attribute WHERE `key` = ?";
        String uuidAttribute = null;

        try {
            uuidAttribute = jdbcTemplate.queryForObject(findAttributeIdQuery, new Object[]{attribute.getKey()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            // Nếu không tìm thấy key, thêm mới vào bảng attribute
            uuidAttribute = UUID.randomUUID().toString();
            String insertAttributeKeyQuery = "INSERT INTO attribute (uuid_attribute, `key`, created_date, updated_date) " +
                    "VALUES (?, ?, NOW(), null)";
            jdbcTemplate.update(insertAttributeKeyQuery, uuidAttribute, attribute.getKey());
        }

        // Thêm value vào bảng product_attribute nếu chưa tồn tại
        String findProductAttributeQuery =
                "SELECT COUNT(*) FROM product_attribute " +
                "WHERE uuid_product = ? AND uuid_attribute = ?";

        int count = jdbcTemplate.queryForObject(findProductAttributeQuery, new Object[]{productID, uuidAttribute}, Integer.class);

        if (count == 0) {
            String insertAttributeValueQuery =
                    "INSERT INTO product_attribute (uuid_attribute, uuid_product, `value`) " +
                    "VALUES (?, ?, ?)";
            jdbcTemplate.update(insertAttributeValueQuery, uuidAttribute, productID, attribute.getValue());
        } else {
            System.out.println("Attribute already exists for product with id = " + productID);
            return null;
        }

        return new AttributeDTOResponse(uuidAttribute, productID, productName, attribute.getKey(), attribute.getValue());
    }

    public AttributeDTOResponse updateAttribute(AttributeDTORequest attributeDTORequest) {
        // Lấy id của attribute từ key
        String getAtrributeIDQuery = "SELECT uuid_attribute FROM attribute WHERE `key` = ?";
        String uuid_attribute = null;

        try {
            uuid_attribute = jdbcTemplate.queryForObject(getAtrributeIDQuery, new Object[]{attributeDTORequest.getKey()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        // Cập nhật bảng attribute
        String updateAttributeQuery =
                "UPDATE attribute " +
                "SET `key` = ?, updated_date = NOW() " +
                "WHERE uuid_attribute = ?";

        jdbcTemplate.update(updateAttributeQuery, attributeDTORequest.getKey(), uuid_attribute);

        // Cập nhật bảng product_attribute
        String updateProductAttributeQuery =
                "UPDATE product_attribute " +
                "SET `value` = ? " +
                "WHERE uuid_attribute = ? AND uuid_product = ?";
        jdbcTemplate.update(updateProductAttributeQuery, attributeDTORequest.getValue(), attributeDTORequest.getUuid_attribute(), attributeDTORequest.getUuid_product());

        // Lấy tên sản phẩm
        String getProductNameQuery = "SELECT `title` FROM product WHERE uuid_product = ?";
        String productName = jdbcTemplate.queryForObject(getProductNameQuery, new Object[]{attributeDTORequest.getUuid_product()}, String.class);

        return new AttributeDTOResponse(uuid_attribute, attributeDTORequest.getUuid_product(),
                productName, attributeDTORequest.getKey(), attributeDTORequest.getValue());
    }

    public AttributeDTOResponse deleteAttribute(String uuid_attribute, String uuid_product) {
        // Lấy tên sản phẩm, key và value để trả về
        String getProductNameQuery = "SELECT `title` FROM product WHERE uuid_product = ?";
        String productName = jdbcTemplate.queryForObject(getProductNameQuery, new Object[]{uuid_product}, String.class);

        String getKeyQuery = "SELECT `key` FROM attribute WHERE uuid_attribute = ?";
        String key = jdbcTemplate.queryForObject(getKeyQuery, new Object[]{uuid_attribute}, String.class);

        String getValueQuery = "SELECT `value` FROM product_attribute WHERE uuid_attribute = ? AND uuid_product = ?";
        String value = jdbcTemplate.queryForObject(getValueQuery, new Object[]{uuid_attribute, uuid_product}, String.class);

        // Xóa ở product_attribute
        String deleteAttributeValueQuery = "DELETE FROM product_attribute WHERE uuid_attribute = ? AND uuid_product = ?";
        jdbcTemplate.update(deleteAttributeValueQuery, uuid_attribute, uuid_product);

        return new AttributeDTOResponse(uuid_attribute, uuid_product, productName, key, value);
    }
}
