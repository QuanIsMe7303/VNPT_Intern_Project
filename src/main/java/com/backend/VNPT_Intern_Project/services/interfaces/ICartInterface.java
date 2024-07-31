package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.cartitemdto.CartItemDTORequest;
import com.backend.VNPT_Intern_Project.dtos.cartitemdto.CartItemDTOResponse;

import java.util.List;

public interface ICartInterface {
    List<CartItemDTOResponse> getCartItemsByUserID(String uuidUser);

    CartItemDTOResponse getCartItemById(String uuidCartItem);

    CartItemDTOResponse addCartItem(String uuidUser, CartItemDTORequest cartItemDTORequest);

    CartItemDTOResponse updateCartItem(String uuidUser, CartItemDTORequest cartItemDTORequest);

    CartItemDTOResponse deleteCartItem(String uuidCartItem);
}
