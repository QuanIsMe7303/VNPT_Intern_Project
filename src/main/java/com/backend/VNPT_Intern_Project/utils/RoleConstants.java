package com.backend.VNPT_Intern_Project.utils;

import lombok.Getter;

@Getter
public enum RoleConstants {
    ADMIN("ADMIN"), // Thêm sua xoa tat ca
    USER("USER"),  //
    SELLER("SELLER"); // Them sua xoa san pham, khong duoc mua hang

    private final String roleName;

    RoleConstants(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

}