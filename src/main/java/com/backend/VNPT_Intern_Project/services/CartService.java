//package com.backend.VNPT_Intern_Project.services;
//
//import com.backend.VNPT_Intern_Project.dtos.CartItemDTO.CartItemDTORequest;
//import com.backend.VNPT_Intern_Project.dtos.CartItemDTO.CartItemDTOResponse;
//import com.backend.VNPT_Intern_Project.repositories.CartRepository;
//import com.backend.VNPT_Intern_Project.services.interfaces.ICartInterface;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CartService implements ICartInterface {
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Override
//    public List<CartItemDTOResponse> getCartItemsByUserID(String uuidUser) {
//        return cartRepository.getCartItemsByUserID(uuidUser);
//    }
//
//    @Override
//    public CartItemDTOResponse getCartItemById(String uuidCartItem) {
//        return cartRepository.getCartItemById(uuidCartItem);
//    }
//
//    @Override
//    public CartItemDTOResponse addCartItem(String uuidCart, CartItemDTORequest cartItemDTORequest) {
//        return cartRepository.addCartItem(uuidCart, cartItemDTORequest);
//    }
//
//    @Override
//    public CartItemDTOResponse updateCartItem(String uuidUser, CartItemDTORequest cartItemDTORequest) {
//        return cartRepository.updateCartItem(uuidUser, cartItemDTORequest);
//    }
//
//    @Override
//    public CartItemDTOResponse deleteCartItem(String uuidCartItem) {
//        return cartRepository.deleteCartItem(uuidCartItem);
//    }
//}
//
