package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;
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

    public List<ProductDTOResponse> getAllProducts() {
        String sql = "SELECT * FROM product p";
        return jdbcTemplate.query(sql, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTOResponse> getProductById(String uuid_product) {
        String sql = "SELECT * FROM product p WHERE p.uuid_product = ?";
        return jdbcTemplate.query(sql, new Object[]{uuid_product}, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTOResponse> getProductsByBrandName(String brand_name) {
        String sql =
                "SELECT * FROM brand b " +
                "LEFT JOIN product p " +
                "ON b.uuid_brand = p.uuid_brand " +
                "WHERE b.name = ?";
        return jdbcTemplate.query(sql, new Object[]{brand_name}, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTOResponse> getProductsByCategoryName(String category_name) {
        String sql =
                "SELECT * FROM category c " +
                "LEFT JOIN product_category pc ON c.uuid_category = pc.uuid_category " +
                "LEFT JOIN product p ON pc.uuid_product = p.uuid_product " +
                "WHERE c.title = ?";
        return jdbcTemplate.query(sql, new Object[]{category_name}, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTOResponse> getProductsByBrandAndCategory(String brand_name, String category_name) {
        String sql =
                "SELECT * FROM category c " +
                "LEFT JOIN product_category pc ON c.uuid_category = pc.uuid_category " +
                "LEFT JOIN product p ON pc.uuid_product = p.uuid_product " +
                "JOIN brand b ON p.uuid_brand = b.uuid_brand " +
                "WHERE b.name = ? AND c.title = ?";
        return jdbcTemplate.query(sql, new Object[]{brand_name, category_name}, new ProductDTOMapper(jdbcTemplate));
    }

    public List<ProductDTOResponse> createProduct(ProductDTORequest product) {
        String createProductQuery =
                "INSERT INTO product (uuid_product, title, meta_title, summary, type, price, quantity, created_date, updated_date, published_date, description, uuid_brand) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), ?, ?, ?)";

        // Tạo id cho product mới
        String newProductID = UUID.randomUUID().toString();

        // Kiểm tra brand đã có chưa, chưa thì tạo mới
        String findBrandIdQuery = "SELECT uuid_brand FROM brand WHERE name = ?";
        String brandID = null;

        try {
            brandID = jdbcTemplate.queryForObject(findBrandIdQuery, new Object[]{product.getBrand()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Brand not found with name: " + product.getBrand());
            String insertBrandQuery =
                    "INSERT INTO brand (uuid_brand, name, created_date) " +
                    "VALUES (?, ?, NOW())";

            // Tạo UUID cho brand
            brandID = UUID.randomUUID().toString();
            jdbcTemplate.update(insertBrandQuery, brandID, product.getBrand());
        }

        // Thêm sản phẩm vào bảng Product
        jdbcTemplate.update(createProductQuery,
                newProductID,
                product.getTitle(),
                product.getMetaTitle(),
                product.getSummary(),
                product.getType(),
                product.getPrice(),
                product.getQuantity(),
                product.getPublishedDate(),
                product.getDescription(),
                brandID);

        // Kiểm tra category, chưa có thì thêm 1 category mới với sản phẩm vừa thêm
        if(product.getCategory() != null) {
            String findCategoryQuery =
                    "SELECT uuid_category FROM category " +
                    "WHERE title = ?";
            String categoryID = null;
            try {
                categoryID = jdbcTemplate.queryForObject(findCategoryQuery, new Object[]{product.getCategory()}, String.class);
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Category not found with name: " + product.getCategory());

                String insertCategory =
                        "INSERT INTO category (uuid_category, title) " +
                        "VALUES (?, ?)";
                categoryID = UUID.randomUUID().toString();
                jdbcTemplate.update(insertCategory, categoryID, product.getCategory());
            }
            String ProductCategoryQuery =
                    "INSERT INTO product_category (uuid_product, uuid_category) " +
                            "VALUES (?, ?)";
            jdbcTemplate.update(ProductCategoryQuery, newProductID, categoryID);
        }

        return getProductById(newProductID);
    }

    public List<ProductDTOResponse> updateProduct(ProductDTORequest product, String uuid_product) {
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
            return null;
        }

        // Kiểm tra brand đã có chưa, nếu chưa thì tạo mới
        String brandID = null;
        try {
            brandID = jdbcTemplate.queryForObject(findBrandIdQuery, new Object[]{product.getBrand()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Brand not found with name: " + product.getBrand());
            String insertBrandQuery =
                    "INSERT INTO brand (uuid_brand, name, created_date, updated_date) " +
                    "VALUES (?, ?, NOW(), NOW())";

            // Tạo UUID cho brand
            brandID = UUID.randomUUID().toString();

            jdbcTemplate.update(insertBrandQuery, brandID, product.getBrand());
        }

        jdbcTemplate.update(updateProductQuery,
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

        // Kiểm tra category, chưa có thì gắn category với sản phẩm
        if(product.getCategory() != null) {
            String findCategoryQuery =
                    "SELECT uuid_category FROM category " +
                    "WHERE title = ?";
            String categoryID = null;

            String ProductCategoryQuery =
                    "INSERT INTO product_category (uuid_product, uuid_category) " +
                    "VALUES (?, ?)";

            try {
                categoryID = jdbcTemplate.queryForObject(findCategoryQuery, new Object[]{product.getCategory()}, String.class);
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Category not found with name: " + product.getCategory());

                String insertCategory =
                        "INSERT INTO category (uuid_category, title) " +
                        "VALUES (?, ?)";
                categoryID = UUID.randomUUID().toString();
                jdbcTemplate.update(insertCategory, categoryID, product.getCategory());


                jdbcTemplate.update(ProductCategoryQuery, uuid_product, categoryID);
            }

            // nếu đã có category, kiểm tra xem sản phẩm đã được gắn với category đó chưa, chưa thì thêm vào product_category
            String checkProductCategory =
                    "SELECT uuid_product FROM product_category " +
                    "WHERE uuid_product = ? AND uuid_category = ?";

            String check = jdbcTemplate.queryForObject(checkProductCategory, new Object[]{uuid_product, categoryID}, String.class);

            if (check == null) {
                jdbcTemplate.update(ProductCategoryQuery, uuid_product, categoryID);
            }
        }
        return getProductById(productID);
    }

    public List<ProductDTOResponse> deleteProduct(String uuid_product) {
        List<ProductDTOResponse> product = getProductById(uuid_product);

        if (product != null) {
            // Xóa sản phẩm ở ProductAttribute
            String deleteProductAtrributeQuery = "DELETE FROM product_attribute WHERE uuid_product = ?";
            jdbcTemplate.update(deleteProductAtrributeQuery, uuid_product);

            // Xóa sản phẩm ở ProductCategory
            String deleteProductCategoryQuery = "DELETE FROM product_category WHERE uuid_product = ?";
            jdbcTemplate.update(deleteProductCategoryQuery, uuid_product);

            // Xóa sản phẩm ở bảng Product
            String sql = "DELETE FROM product WHERE uuid_product = ?";
            jdbcTemplate.update(sql, uuid_product);
        }
        return product;
    }
}

