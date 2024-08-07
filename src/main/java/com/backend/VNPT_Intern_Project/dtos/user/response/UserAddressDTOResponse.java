package com.backend.VNPT_Intern_Project.dtos.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserAddressDTOResponse {
    private String uuidUser;
    private String uuidAddress;
    private String street;
    private String district;
    private String city;
    private String mobile;
    private Integer postalCode;
}
