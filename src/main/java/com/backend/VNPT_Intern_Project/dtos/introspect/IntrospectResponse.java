package com.backend.VNPT_Intern_Project.dtos.introspect;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class IntrospectResponse {
    boolean valid;
}
