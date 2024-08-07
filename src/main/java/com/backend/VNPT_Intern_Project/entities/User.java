package com.backend.VNPT_Intern_Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_user", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidUser;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description")
    private String description;

    @Column(name = "password")
    private String password;

    @Column(name = "admin")
    private Integer admin;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "activate")
    private Integer activate;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<UserAddress> addressList;

//    @ManyToOne
//    @JoinColumn(name = "uuid_role")
//    @JsonIgnoreProperties({"userList"})
//    private Role role;

//    @ManyToMany
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "uuid_user"),
            inverseJoinColumns = @JoinColumn(name = "uuid_role")
    )
    private Set<Role> roleSet;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<CartItem> cartItems;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Order> orderList;
}
