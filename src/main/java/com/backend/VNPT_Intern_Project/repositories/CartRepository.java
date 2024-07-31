//package com.backend.VNPT_Intern_Project.repositories;
//
//import com.backend.VNPT_Intern_Project.dtos.CartItemDTO.CartItemDTORequest;
//import com.backend.VNPT_Intern_Project.dtos.CartItemDTO.CartItemDTOResponse;
//import com.backend.VNPT_Intern_Project.dtos.ProductDTO.ProductDTOResponse;
//import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
//import com.backend.VNPT_Intern_Project.repositories.DTOMapper.CartItemDTOMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public class CartRepository {
//    @Autowired
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public CartRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<CartItemDTOResponse> getCartItemsByUserID(String uuidUser) {
//        String findCartQuery = "SELECT uuid_cart FROM cart WHERE uuid_user = ?";
//        String uuidCart = jdbcTemplate.queryForObject(findCartQuery, new Object[]{uuidUser}, String.class);
//
//        if (uuidCart == null) {
//            throw new ResourceNotFoundException("Cart not found for user: " + uuidUser);
//        }
//
//        String findCartItemsQuery = "SELECT ci.* FROM cart_item ci WHERE ci.uuid_cart = ?";
//        return jdbcTemplate.query(findCartItemsQuery, new Object[]{uuidCart}, new CartItemDTOMapper(jdbcTemplate));
//    }
//
//    public CartItemDTOResponse getCartItemById(String uuidCartItem) {
//        String getCartItemQuery =
//                "SELECT ci.*, c.uuid_cart FROM cart_item ci " +
//                "JOIN cart c ON ci.uuid_cart = c.uuid_cart " +
//                "WHERE uuid_cart_item = ?";
//        return jdbcTemplate.queryForObject(getCartItemQuery, new Object[]{uuidCartItem}, new CartItemDTOMapper(jdbcTemplate));
//    }
//
//    public CartItemDTOResponse addCartItem(String uuid_cart, CartItemDTORequest cartItemDTORequest) {
//        // product
//        ProductDTOResponse product = null;
//        try {
//            product = productRepository.getProductById(cartItemDTORequest.getUuid_product());
//        } catch (EmptyResultDataAccessException e) {
//            throw new ResourceNotFoundException("Product not found with id: " + cartItemDTORequest.getUuid_product());
//        }
//
//        // Tạo cart item
//        String uuidCartItem = UUID.randomUUID().toString();
//        String insertCartItemQuery =
//                "INSERT INTO cart_item (uuid_cart_item, uuid_cart, uuid_product, price, discount, quantity, content, created_date) " +
//                        "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
//        jdbcTemplate.update(insertCartItemQuery, uuidCartItem, uuid_cart,
//                cartItemDTORequest.getUuid_product(), product.getPrice(),
//                cartItemDTORequest.getDiscount(), cartItemDTORequest.getQuantity(),
//                cartItemDTORequest.getContent());
//
//        return new CartItemDTOResponse(uuidCartItem, uuid_cart, product, cartItemDTORequest.getPrice(), cartItemDTORequest.getDiscount(), cartItemDTORequest.getQuantity(), cartItemDTORequest.getContent());
//    }
//
//    public CartItemDTOResponse updateCartItem(String uuidCartItem, CartItemDTORequest cartItemDTORequest) {
//        // product
//        ProductDTOResponse product = productRepository.getProductById(cartItemDTORequest.getUuid_product());
//
//        String findCartItemQuery =
//                "SELECT uuid_cart_item FROM cart_item " +
//                "WHERE uuid_cart_item = ?";
//        String uuidCartItemInDB = jdbcTemplate.queryForObject(findCartItemQuery, new Object[]{uuidCartItem}, String.class);
//
//        if (uuidCartItemInDB == null) {
//            throw new ResourceNotFoundException("Cart item not found with id: " + uuidCartItem);
//        }
//
//        String updateCartItemQuery =
//                "UPDATE cart_item " +
//                "SET uuid_product = ?, price = ?, discount = ?, quantity = ?, content = ?, updated_date = NOW() " +
//                "WHERE uuid_cart_item = ?";
//        jdbcTemplate.update(updateCartItemQuery, cartItemDTORequest.getUuid_product(),
//                cartItemDTORequest.getPrice(), cartItemDTORequest.getDiscount(),
//                cartItemDTORequest.getQuantity(), cartItemDTORequest.getContent(), uuidCartItem);
//
//        return new CartItemDTOResponse(uuidCartItem, uuidCartItemInDB, product, cartItemDTORequest.getPrice(), cartItemDTORequest.getDiscount(), cartItemDTORequest.getQuantity(), cartItemDTORequest.getContent());
//    }
//
//    public CartItemDTOResponse deleteCartItem(String uuidCartItem) {
//        CartItemDTOResponse deletedCart = getCartItemById(uuidCartItem);
//        String deleteCartItemQuery = "DELETE FROM cart_item WHERE uuid_cart_item = ?";
//        jdbcTemplate.update(deleteCartItemQuery, uuidCartItem);
//        return deletedCart;
//    }
//
//}
