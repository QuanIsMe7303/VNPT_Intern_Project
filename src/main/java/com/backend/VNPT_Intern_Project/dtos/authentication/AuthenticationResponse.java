package com.backend.VNPT_Intern_Project.dtos.authentication;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AuthenticationResponse {
    private String token;
    private boolean authenticated;
}
