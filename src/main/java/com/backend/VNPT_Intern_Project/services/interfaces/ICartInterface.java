package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.cartitem.CartItemDTORequest;
import com.backend.VNPT_Intern_Project.dtos.cartitem.CartItemDTOResponse;

import java.util.List;

public interface ICartInterface {
    List<CartItemDTOResponse> getCartItemsByUserID(String uuidUser);

    CartItemDTOResponse getCartItemById(String uuidCartItem);

    CartItemDTOResponse addCartItem(String uuidCart, CartItemDTORequest cartItemDTORequest);

    CartItemDTOResponse updateCartItem(String uuidCartItem, CartItemDTORequest cartItemDTORequest);

    CartItemDTOResponse deleteCartItem(String uuidCartItem);
}
