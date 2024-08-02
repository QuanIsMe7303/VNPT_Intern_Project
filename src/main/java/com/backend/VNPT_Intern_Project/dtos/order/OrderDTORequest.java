package com.backend.VNPT_Intern_Project.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTORequest {
    private String userName;
    private String sessionId;
    private String token;
    private String promo;
    private Double discount;
    private String phone;
    private String paymentMethods;
    private String note;
    private String addressShip;
}
