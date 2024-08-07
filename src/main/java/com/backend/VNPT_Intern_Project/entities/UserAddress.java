package com.backend.VNPT_Intern_Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_address", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidAddress;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "district")
    private String district;

    @Column(name = "postal_code")
    private Integer postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "uuid_user")
    private User user;
}
