package com.backend.VNPT_Intern_Project.dtos.order;

import com.backend.VNPT_Intern_Project.dtos.orderitem.OrderItemResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTOResponse {
    private String uuidOrder;
    private String userName;
    private Integer status;
    private Double subtotal;
    private Double itemDiscount;
    private Double tax;
    private Double shipping;
    private Double total;
    private String promo;
    private Double discount;
    private Double grandTotal;
    private String phone;
    private String paymentMethods;
    private String note;
    private String addressShip;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<OrderItemResponse> orderItemList;
}
