package com.backend.VNPT_Intern_Project.utils;


import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(1),
    CONFIRMED(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELLED(0);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

}