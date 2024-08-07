package com.backend.VNPT_Intern_Project.dtos.user.response;

import com.backend.VNPT_Intern_Project.dtos.role.RoleDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTOResponse {
    private String uuidUser;

    private String firstName;

    private String middleName;

    private String lastName;

    private String mobile;

    private String email;

    private String avatar;

    private String description;

    private Set<RoleDTOResponse> roles;

    private List<UserAddressResponse> addressList;

    private LocalDateTime registerDate;

    private LocalDateTime lastLogin;

    private Integer activate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAddressResponse {
         private String uuidAddress;
         private String mobile;
         private String street;
         private String district;
         private String city;
         private Integer postalCode;
    }
}
