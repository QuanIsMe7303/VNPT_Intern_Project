package com.backend.VNPT_Intern_Project.utils;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public static boolean isValidRole(String roleName) {
        return Arrays.stream(RoleConstants.values())
                .anyMatch(role -> role.roleName.equals(roleName));
    }

    public static List<String> getAllRoleNames() {
        return Arrays.stream(RoleConstants.values())
                .map(RoleConstants::toString)
                .collect(Collectors.toList());
    }
}