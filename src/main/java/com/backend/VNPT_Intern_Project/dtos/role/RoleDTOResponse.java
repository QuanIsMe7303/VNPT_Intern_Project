package com.backend.VNPT_Intern_Project.dtos.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleDTOResponse implements Serializable {
    private String uuidRole;
    private String name;
    private Set<String> permissionSet;
}
