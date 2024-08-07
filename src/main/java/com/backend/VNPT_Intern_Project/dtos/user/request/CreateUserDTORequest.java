package com.backend.VNPT_Intern_Project.dtos.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateUserDTORequest {
    @NotNull
    private String email;
    @NotNull
    private String password;

    private String phone;
    private String firstName;
    private String middleName;
    private String lastName;
    private String description;
}
