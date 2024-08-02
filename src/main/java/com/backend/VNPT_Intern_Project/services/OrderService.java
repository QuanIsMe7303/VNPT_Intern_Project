package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.order.OrderDTORequest;
import com.backend.VNPT_Intern_Project.dtos.order.OrderDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.orderitem.OrderItemResponse;
import com.backend.VNPT_Intern_Project.entities.*;
import com.backend.VNPT_Intern_Project.exception.ApiRequestException;
import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
import com.backend.VNPT_Intern_Project.repositories.*;
import com.backend.VNPT_Intern_Project.services.interfaces.IOrderInterface;
import com.backend.VNPT_Intern_Project.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Override
    public OrderDTOResponse createOrder(String uuidUser, OrderDTORequest orderDTORequest) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + uuidUser));

        List<CartItem> cartItemList = user.getCartItems();

        Order order = new Order();
        double total;
        double grandTotal;
        double subtotal = 0.0;
        double itemDiscount = 0.0;

        for (CartItem cartItem : cartItemList) {
            // Kiểm tra số lượng sp
            Product product = cartItem.getProduct();
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new ApiRequestException("Product is out of stock!");
            }

            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            double itemTotal = product.getPrice() * cartItem.getQuantity();
            double itemDiscountValue = itemTotal * cartItem.getDiscount();
            double itemPriceAfterDiscount = itemTotal - itemDiscountValue;

            subtotal += itemTotal;
            itemDiscount += itemDiscountValue;
        }

        double tax = calculateTax(subtotal - itemDiscount);
        double shipping = calculateShipping(orderDTORequest);
        total = subtotal - itemDiscount;
        grandTotal = total + tax + shipping;

        // set order
        order.setUuidUser(uuidUser);
        order.setSessionId(orderDTORequest.getSessionId());
        order.setToken(orderDTORequest.getToken());
        order.setStatus(OrderStatus.PENDING.getValue());
        order.setSubtotal(subtotal);
        order.setItemDiscount(itemDiscount);
        order.setTax(tax);
        order.setShipping(shipping);
        order.setTotal(total);
        order.setPromo(orderDTORequest.getPromo());
        order.setDiscount(orderDTORequest.getDiscount());
        order.setGrandTotal(grandTotal);
        order.setPhone(orderDTORequest.getPhone());
        order.setPaymentMethods(orderDTORequest.getPaymentMethods());
        order.setNote(orderDTORequest.getNote());
        order.setAddressShip(orderDTORequest.getAddressShip());
        order.setUser(user);

        // Lưu order
        Order savedOrder = orderRepository.save(order);

        // Tạo và lưu OrderItem
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setContent(cartItem.getContent());
            orderItem.setOrder(savedOrder);

            // Lưu OrderItem
            orderItem = orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);
        }

        // Cập nhật Order với danh sách OrderItem mới
        savedOrder.setOrderItemList(orderItemList);
        savedOrder = orderRepository.save(savedOrder);

        // Xóa giỏ hàng sau khi đặt hàng thành công
        clearCart(user);

        return convertToOrderDTOResponse(savedOrder);
    }

    private void clearCart(User user) {
        List<CartItem> cartItemList = user.getCartItems();
        for (CartItem cartItem : cartItemList) {
            cartItemRepository.delete(cartItem);
        }
    }

    private double calculateTax(double total) {
        return total * 0.1;
    }

    private double calculateShipping(OrderDTORequest orderDTO) {
        return 50000;
    }

    private OrderDTOResponse convertToOrderDTOResponse(Order order) {
        OrderDTOResponse orderDTOResponse = new OrderDTOResponse();
        orderDTOResponse.setUuidOrder(order.getUuidOrder());
        orderDTOResponse.setUserName(order.getUser().getFirstName() + " " + order.getUser().getMiddleName() + " " + order.getUser().getLastName());
        orderDTOResponse.setStatus(order.getStatus());
        orderDTOResponse.setSubtotal(order.getSubtotal());
        orderDTOResponse.setItemDiscount(order.getItemDiscount());
        orderDTOResponse.setTax(order.getTax());
        orderDTOResponse.setShipping(order.getShipping());
        orderDTOResponse.setTotal(order.getTotal());
        orderDTOResponse.setPromo(order.getPromo());
        orderDTOResponse.setDiscount(order.getDiscount());
        orderDTOResponse.setGrandTotal(order.getGrandTotal());
        orderDTOResponse.setPhone(order.getPhone());
        orderDTOResponse.setPaymentMethods(order.getPaymentMethods());
        orderDTOResponse.setNote(order.getNote());
        orderDTOResponse.setAddressShip(order.getAddressShip());
        orderDTOResponse.setCreatedDate(order.getCreatedDate());
        orderDTOResponse.setUpdatedDate(order.getUpdatedDate());
        orderDTOResponse.setOrderItemList(order.getOrderItemList().stream()
                .map(this::convertToOrderItemResponse)
                .collect(Collectors.toList()));

        return orderDTOResponse;
    }

    private OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setUuidProduct(orderItem.getProduct().getUuidProduct());
        orderItemResponse.setPrice(orderItem.getPrice());
        orderItemResponse.setDiscount(orderItem.getDiscount());
        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setContent(orderItem.getContent());
        return orderItemResponse;
    }
}
