package com.backend.VNPT_Intern_Project.dtos.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
