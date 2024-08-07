package com.backend.VNPT_Intern_Project.dtos.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdateUserDTORequest {
    private String email;
    private String phone;
    private String firstName;
    private String middleName;
    private String lastName;
    private String description;
    private String avatar;
    private List<String> roles;
}
