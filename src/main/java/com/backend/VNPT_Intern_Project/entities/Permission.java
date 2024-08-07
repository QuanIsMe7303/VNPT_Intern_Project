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
@Table(name = "permission")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Permission {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_permission", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidPermission;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "permissionSet", fetch = FetchType.LAZY)
//    @JsonIgnore
    @JsonIgnoreProperties("permissionSet")
    private Set<Role> roleSet = new HashSet<>();

}
