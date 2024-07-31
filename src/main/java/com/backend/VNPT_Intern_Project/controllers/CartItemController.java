//package com.backend.VNPT_Intern_Project.controllers;
//
//import com.backend.VNPT_Intern_Project.dtos.ApiResponse;
//import com.backend.VNPT_Intern_Project.dtos.CartItemDTO.CartItemDTORequest;
//import com.backend.VNPT_Intern_Project.dtos.CartItemDTO.CartItemDTOResponse;
//import com.backend.VNPT_Intern_Project.exception.ApiRequestException;
//import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
////import com.backend.VNPT_Intern_Project.services.CartService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/cart")
//public class CartItemController {
//    @Autowired
////    private CartService cartService;
//
//    @GetMapping("cart-items/{id}")
//    public ResponseEntity<?> getCartItemById(@PathVariable String id) {
//        try {
//            CartItemDTOResponse cartItemDTOResponse = cartService.getCartItemById(id);
//            if (cartItemDTOResponse == null) {
//                throw new ResourceNotFoundException("Cart item not found with id: " + id);
//            } else {
//                ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", cartItemDTOResponse);
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//        } catch (ResourceNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new ApiRequestException("Error while fetching cart item: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getCartItemsByUserID(@PathVariable String userId) {
//        try {
//            List<CartItemDTOResponse> cartItems = cartService.getCartItemsByUserID(userId);
//            if (cartItems.isEmpty()) {
//                throw new ResourceNotFoundException("Cart items are not found");
//            } else {
//                ApiResponse<List<CartItemDTOResponse>> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", cartItems);
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//        } catch (ResourceNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new ApiRequestException("Error while fetching cart: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/{uuid_cart}")
//    public ResponseEntity<?> addProductToCart(@Valid @RequestBody CartItemDTORequest cartItemDTORequest, @PathVariable String uuid_cart) {
//        try {
//            CartItemDTOResponse cartItem = cartService.addCartItem(uuid_cart, cartItemDTORequest);
//            ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", cartItem);
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (Exception e) {
//            throw new ApiRequestException("Error: " + e.getMessage());
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateCartItem(@Valid @RequestBody CartItemDTORequest cartItemDTORequest, @PathVariable String id) {
////        try {
//            CartItemDTOResponse cartItemDTOResponse = cartService.updateCartItem(id, cartItemDTORequest);
//            ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", cartItemDTOResponse);
//            return new ResponseEntity<>(response, HttpStatus.OK);
////        } catch (Exception e) {
////            throw new ApiRequestException("Error: " + e.getMessage());
////        }
//    }
//
//    @DeleteMapping("/{uuid_cart_item}")
//    public ResponseEntity<?> deleteCartItem(@PathVariable String uuid_cart_item) {
////        try {
//            CartItemDTOResponse cartItemDTOResponse = cartService.deleteCartItem(uuid_cart_item);
//            ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "SUCCESS", cartItemDTOResponse);
//            return new ResponseEntity<>(response, HttpStatus.OK);
////        } catch (Exception e) {
////            throw new ApiRequestException("Error: " + e.getMessage());
////        }
//    }
//}
