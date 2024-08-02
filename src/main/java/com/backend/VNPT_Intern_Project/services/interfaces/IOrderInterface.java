package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.order.OrderDTORequest;
import com.backend.VNPT_Intern_Project.dtos.order.OrderDTOResponse;
import com.backend.VNPT_Intern_Project.entities.Order;

public interface IOrderInterface {
    OrderDTOResponse createOrder(String uuidUser, OrderDTORequest orderDTORequest);
}
