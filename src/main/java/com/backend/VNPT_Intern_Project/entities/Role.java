package com.backend.VNPT_Intern_Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_role", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidRole;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roleSet", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> userSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
//    @JsonIgnore
    @JsonIgnoreProperties("roleSet")
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "uuid_role"),
            inverseJoinColumns = @JoinColumn(name = "uuid_permission")
    )
    private Set<Permission> permissionSet = new HashSet<>();

}
