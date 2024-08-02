package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.ApiResponse;
import com.backend.VNPT_Intern_Project.dtos.order.OrderDTORequest;
import com.backend.VNPT_Intern_Project.dtos.order.OrderDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.product.ProductDTORequest;
import com.backend.VNPT_Intern_Project.dtos.product.ProductDTOResponse;
import com.backend.VNPT_Intern_Project.entities.Order;
import com.backend.VNPT_Intern_Project.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("{uuidUser}")
    public ResponseEntity<?> createOrder(@PathVariable String uuidUser, @Valid @RequestBody OrderDTORequest orderRequest) {
        OrderDTOResponse order = orderService.createOrder(uuidUser, orderRequest);
        ApiResponse<OrderDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", order);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
