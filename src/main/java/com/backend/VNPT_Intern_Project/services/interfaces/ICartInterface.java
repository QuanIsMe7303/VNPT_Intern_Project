package com.backend.vnptproject.services.interfaces;

import com.backend.vnptproject.dtos.cartitemdto.CartItemDTORequest;
import com.backend.vnptproject.dtos.cartitemdto.CartItemDTOResponse;

import java.util.List;

public interface ICartInterface {
    List<CartItemDTOResponse> getCartItemsByUserID(String uuidUser);

    CartItemDTOResponse getCartItemById(String uuidCartItem);

    CartItemDTOResponse addCartItem(String uuidUser, CartItemDTORequest cartItemDTORequest);

    CartItemDTOResponse updateCartItem(String uuidUser, CartItemDTORequest cartItemDTORequest);

    CartItemDTOResponse deleteCartItem(String uuidCartItem);
}
