package com.backend.VNPT_Intern_Project.dtos.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserAddressDTORequest {
    private String street;
    private String district;
    private String city;
    private String mobile;
    private Integer postalCode;
}
