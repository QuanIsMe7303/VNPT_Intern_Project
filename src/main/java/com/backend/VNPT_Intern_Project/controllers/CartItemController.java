package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.ApiResponse;
import com.backend.VNPT_Intern_Project.dtos.cartitem.CartItemDTORequest;
import com.backend.VNPT_Intern_Project.dtos.cartitem.CartItemDTOResponse;
import com.backend.VNPT_Intern_Project.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartItemController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart-items/{id}")
    public ResponseEntity<?> getCartItemById(@PathVariable String id) {
        CartItemDTOResponse cartItemDTOResponse = cartService.getCartItemById(id);
        ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", cartItemDTOResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartItemsByUserID(@PathVariable String userId) {
        List<CartItemDTOResponse> cartItems = cartService.getCartItemsByUserID(userId);
        ApiResponse<List<CartItemDTOResponse>> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", cartItems);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{uuid_cart}")
    public ResponseEntity<?> addProductToCart(@Valid @RequestBody CartItemDTORequest cartItemDTORequest, @PathVariable String uuid_cart) {
        CartItemDTOResponse cartItem = cartService.addCartItem(uuid_cart, cartItemDTORequest);
        ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", cartItem);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/cart-items/{uuid_cart_item}")
    public ResponseEntity<?> updateCartItem(@Valid @RequestBody CartItemDTORequest cartItemDTORequest, @PathVariable String uuid_cart_item) {
        CartItemDTOResponse cartItemDTOResponse = cartService.updateCartItem(uuid_cart_item, cartItemDTORequest);
        ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", cartItemDTOResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/cart-items/{uuid_cart_item}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String uuid_cart_item) {
        CartItemDTOResponse cartItemDTOResponse = cartService.deleteCartItem(uuid_cart_item);
        ApiResponse<CartItemDTOResponse> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "SUCCESS", cartItemDTOResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
