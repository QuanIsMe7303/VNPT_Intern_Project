package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.cartitem.CartItemDTORequest;
import com.backend.VNPT_Intern_Project.dtos.cartitem.CartItemDTOResponse;
import com.backend.VNPT_Intern_Project.entities.CartItem;
import com.backend.VNPT_Intern_Project.entities.Product;
import com.backend.VNPT_Intern_Project.entities.User;
import com.backend.VNPT_Intern_Project.exception.ConflictException;
import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
import com.backend.VNPT_Intern_Project.repositories.CartRepository;
import com.backend.VNPT_Intern_Project.repositories.ProductRepository;
import com.backend.VNPT_Intern_Project.repositories.UserRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.ICartInterface;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartService implements ICartInterface {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<CartItemDTOResponse> getCartItemsByUserID(String uuidUser) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + uuidUser));

        List<CartItem> cartItems = cartRepository.findByUuidUser(uuidUser);

        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Cart items is not found!");
        }
        return cartItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public CartItemDTOResponse getCartItemById(String uuidCartItem) {
        CartItem cartItem = cartRepository.findById(uuidCartItem)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item is not found with id: " + uuidCartItem));

        return convertToDTO(cartItem);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public CartItemDTOResponse addCartItem(String uuidUser, CartItemDTORequest cartItemDTORequest) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + uuidUser));

        Product product = productRepository.findById(cartItemDTORequest.getUuidProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + cartItemDTORequest.getUuidProduct()));

        if (product.getQuantity() < cartItemDTORequest.getQuantity()) {
            throw new ConflictException("Requested quantity exceeds available stock. Available: " + product.getQuantity());
        }

        // Neu san pham da co trong gio thì khong them dc
        boolean productExistsInCart = cartRepository.existsByCartAndProduct(uuidUser, cartItemDTORequest.getUuidProduct());
        if (productExistsInCart) {
            throw new ConflictException("Product already exists in the cart.");
        }

        CartItem newCartItem = new CartItem();

//        newCartItem.setUuidCart(user.getUuidCart());
        newCartItem.setUuidUser(user.getUuidUser());
        newCartItem.setUuidProduct(cartItemDTORequest.getUuidProduct());
        newCartItem.setPrice(product.getPrice() * (1 - cartItemDTORequest.getDiscount()) * cartItemDTORequest.getQuantity());
        newCartItem.setDiscount(cartItemDTORequest.getDiscount());
        newCartItem.setQuantity(cartItemDTORequest.getQuantity());
        newCartItem.setActive(cartItemDTORequest.getActive());
        newCartItem.setProduct(product);

        if (cartItemDTORequest.getContent() != null) {
            newCartItem.setContent(cartItemDTORequest.getContent());
        }

        newCartItem.setUser(user);

        cartRepository.save(newCartItem);

        return convertToDTO(newCartItem);
    }


    @Transactional
    @PreAuthorize("hasRole('USER')")
    public CartItemDTOResponse updateCartItem(String uuidCartItem, CartItemDTORequest cartItemDTORequest) {
        CartItem cartItem = cartRepository.findById(uuidCartItem)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item is not found with id: " + uuidCartItem));

        Product product = productRepository.findById(cartItem.getUuidProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + cartItem.getUuidProduct()));

        cartItem.setDiscount(cartItemDTORequest.getDiscount());
        cartItem.setQuantity(cartItemDTORequest.getQuantity());
        cartItem.setActive(cartItemDTORequest.getActive());

        if (cartItemDTORequest.getContent() != null) cartItem.setContent(cartItemDTORequest.getContent());

        cartItem.setPrice(product.getPrice() * (1- cartItemDTORequest.getDiscount()) * cartItemDTORequest.getQuantity());

        cartRepository.save(cartItem);

        return convertToDTO(cartItem);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public CartItemDTOResponse deleteCartItem(String uuidCartItem) {
        CartItem cartItem = cartRepository.findById(uuidCartItem)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item is not found with id: " + uuidCartItem));

        cartRepository.delete(cartItem);
        return convertToDTO(cartItem);
    }

    private CartItemDTOResponse convertToDTO(CartItem cartItem) {
        CartItemDTOResponse dto = new CartItemDTOResponse();
        dto.setUuidCartItem(cartItem.getUuidCartItem());
        dto.setUuidUser(cartItem.getUuidUser());

        Product product = productRepository.findById(cartItem.getUuidProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found with id: " + cartItem.getUuidProduct()));

        CartItemDTOResponse.ProductCartItemResponse productCartItemResponse = new CartItemDTOResponse.ProductCartItemResponse();
        productCartItemResponse.setUuidProduct(product.getUuidProduct());
        productCartItemResponse.setProductName(product.getTitle());
        productCartItemResponse.setPrice(product.getPrice());
        productCartItemResponse.setQuantityInStock(product.getQuantity());

        dto.setProduct(productCartItemResponse);
        dto.setPrice(cartItem.getPrice());
        dto.setDiscount(cartItem.getDiscount());
        dto.setQuantity(cartItem.getQuantity());
        dto.setActive(cartItem.getActive());
        dto.setContent(cartItem.getContent());

        return dto;
    }
}

