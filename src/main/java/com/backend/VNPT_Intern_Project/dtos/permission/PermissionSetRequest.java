package com.backend.VNPT_Intern_Project.dtos.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PermissionSetRequest {
    private String permissionName;
    private String roleName;
}
