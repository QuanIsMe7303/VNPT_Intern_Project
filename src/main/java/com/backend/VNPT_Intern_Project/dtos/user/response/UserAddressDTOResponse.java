package com.backend.VNPT_Intern_Project.dtos.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserAddressDTOResponse implements Serializable {
    private String uuidUser;
    private String uuidAddress;
    private String street;
    private String district;
    private String city;
    private String mobile;
    private Integer postalCode;
}
