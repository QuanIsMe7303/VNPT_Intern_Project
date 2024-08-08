package com.backend.VNPT_Intern_Project.dtos.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddNewAddressRequest {
    @NotNull
    private String street;

    @NotNull
    private String district;

    @NotNull
    private String city;

    private String mobile;

    private Integer postalCode;
}
