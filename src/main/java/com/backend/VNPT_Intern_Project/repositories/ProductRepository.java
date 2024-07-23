package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTO;
import com.backend.VNPT_Intern_Project.repositories.DTOMapper.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductDTO> getAllProducts() {
        String sql = "SELECT * FROM product p";
        return jdbcTemplate.query(sql, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTO> getProductById(String uuid_product) {
        String sql = "SELECT * FROM product p WHERE p.uuid_product = ?";
        return jdbcTemplate.query(sql, new Object[]{uuid_product}, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTO> getProductsByBrandName(String brand_name) {
        String sql = "SELECT * FROM brand b " +
                "LEFT JOIN product p " +
                "ON b.uuid_brand = p.uuid_brand " +
                "WHERE b.name = ?";
        return jdbcTemplate.query(sql, new Object[]{brand_name}, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTO> getProductsByCategoryName(String category_name) {
        String sql = "SELECT * FROM category c " +
                "LEFT JOIN product_category pc ON c.uuid_category = pc.uuid_category " +
                "LEFT JOIN product p ON pc.uuid_product = p.uuid_product " +
                "WHERE c.title = ?";
        return jdbcTemplate.query(sql, new Object[]{category_name}, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTO> getProductsByBrandAndCategory(String brand_name, String category_name) {
        String sql = "SELECT * FROM category c " +
                "LEFT JOIN product_category pc ON c.uuid_category = pc.uuid_category " +
                "LEFT JOIN product p ON pc.uuid_product = p.uuid_product " +
                "JOIN brand b ON p.uuid_brand = b.uuid_brand " +
                "WHERE b.name = ? AND c.title = ?";
        return jdbcTemplate.query(sql, new Object[]{brand_name, category_name}, new ProductDTOMapper(jdbcTemplate));
    }

    public int createProduct(ProductDTO product) {
        String createProductQuery =
                "INSERT INTO product (uuid_product, title, meta_title, summary, type, price, quantity, created_date, updated_date, published_date, description, uuid_brand) " +
                        "VALUES (UUID(), ?, ?, ?, ?, ?, ?, NOW(), NOW(), NOW(), ?, ?)";

        String findBrandIdQuery = "SELECT uuid_brand FROM brand WHERE name = ?";
        String brandID = null;

        try {
            brandID = jdbcTemplate.queryForObject(findBrandIdQuery, new Object[]{product.getBrand()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Brand not found with name: " + product.getBrand());
            String insertBrandQuery = "INSERT INTO brand (uuid_brand, name, created_date, updated_date) VALUES (?, ?, NOW(), NOW())";

            // Tạo UUID cho brand
            brandID = UUID.randomUUID().toString();

            jdbcTemplate.update(insertBrandQuery, brandID, product.getBrand());
        }

        return jdbcTemplate.update(createProductQuery,
                product.getTitle(),
                product.getMetaTitle(),
                product.getSummary(),
                product.getType(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                brandID);
    }

    public int updateProduct(ProductDTO product, String uuid_product) {
        String findProductIDQuery = "SELECT uuid_product FROM product WHERE uuid_product = ?";
        String findBrandIdQuery = "SELECT uuid_brand FROM brand WHERE name = ?";

        String updateProductQuery =
                "UPDATE product " +
                "SET title = ?, meta_title = ?, summary = ?, type = ?, price = ?, quantity = ?, updated_date = NOW(), " +
                "published_date = ?, description = ?, uuid_brand = ? " +
                "WHERE uuid_product = ?";

        String productID = null;
        try {
            productID = jdbcTemplate.queryForObject(findProductIDQuery, new Object[]{uuid_product}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Không tìm thấy sản phẩm!");
            return 0;
        }

        // Kiểm tra brand đã có chưa, nếu chưa thì tạo mới
        String brandID = null;
        try {
            brandID = jdbcTemplate.queryForObject(findBrandIdQuery, new Object[]{product.getBrand()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Brand not found with name: " + product.getBrand());
            String insertBrandQuery = "INSERT INTO brand (uuid_brand, name, created_date, updated_date) VALUES (?, ?, NOW(), NOW())";

            // Tạo UUID cho brand
            brandID = UUID.randomUUID().toString();

            jdbcTemplate.update(insertBrandQuery, brandID, product.getBrand());
        }

        if (productID != null) {
            return jdbcTemplate.update(updateProductQuery,
                    product.getTitle(),
                    product.getMetaTitle(),
                    product.getSummary(),
                    product.getType(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getPublishedDate(),
                    product.getDescription(),
                    brandID,
                    productID);
        }
        return 0;
    }

    public int deleteProduct(String uuid_product) {
        // Xóa sản phẩm ở ProductAttribute
        String deleteProductAtrributeQuery = "DELETE FROM product_attribute WHERE uuid_product = ?";
        int deletedPA = jdbcTemplate.update(deleteProductAtrributeQuery, uuid_product);

        // Xóa sản phẩm ở ProductCategory
        String deleteProductCategoryQuery = "DELETE FROM product_category WHERE uuid_product = ?";
        int deletedCategory = jdbcTemplate.update(deleteProductCategoryQuery, uuid_product);

        // Xóa sản phẩm ở bảng Product
        String sql = "DELETE FROM product WHERE uuid_product = ?";
        return jdbcTemplate.update(sql, uuid_product) + deletedPA + deletedCategory;
    }
}
