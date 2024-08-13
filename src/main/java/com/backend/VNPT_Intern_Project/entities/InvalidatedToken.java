package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InvalidatedToken {
    @Id
    private String id;
    private Date expiryTime;
}
