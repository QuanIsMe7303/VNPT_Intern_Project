package com.backend.VNPT_Intern_Project.dtos.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateUserDTORequest {
    @NotNull
    @Email(message = "is not valid")
    private String email;

    @NotNull
    @Size(min = 8, message = "length must be at least 8 characters")
    private String password;

    private String phone;
    private String firstName;
    private String middleName;
    private String lastName;
    private String description;
}
