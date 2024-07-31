//package com.backend.VNPT_Intern_Project.repositories.DTOMapper;
//
//import com.backend.VNPT_Intern_Project.dtos.CartItemDTO.CartItemDTOResponse;
//import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//public class CartItemDTOMapper implements RowMapper<CartItemDTOResponse> {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public CartItemDTOMapper(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public CartItemDTOResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
//        CartItemDTOResponse cartItemDTOResponse = new CartItemDTOResponse();
//
//        // product
//        String getProductQuery = "SELECT * FROM product p WHERE p.uuid_product = ?";
//        List<ProductDTOResponse> products = jdbcTemplate.query(getProductQuery, new Object[]{rs.getString("ci.uuid_product")}, new ProductDTOMapper(jdbcTemplate));
//        cartItemDTOResponse.setProduct(products.get(0));
//
//        Double productPrice = jdbcTemplate.queryForObject("SELECT price FROM product WHERE uuid_product = ?", new Object[]{rs.getString("ci.uuid_product")}, Double.class);
//
//        // other attributes
//        cartItemDTOResponse.setUuid_cart_item(rs.getString("ci.uuid_cart_item"));
//        cartItemDTOResponse.setUuid_cart(rs.getString("ci.uuid_cart"));
//        cartItemDTOResponse.setPrice(productPrice);
//        cartItemDTOResponse.setQuantity(rs.getShort("ci.quantity"));
//        cartItemDTOResponse.setDiscount(rs.getDouble("ci.discount"));
//        cartItemDTOResponse.setContent(rs.getString("ci.content"));
//
//        return cartItemDTOResponse;
//    }
//}
