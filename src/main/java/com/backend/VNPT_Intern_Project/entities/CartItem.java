package com.backend.VNPT_Intern_Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_cart_item", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidCartItem;

    @NotNull
    @Column(name = "uuid_user")
    private String uuidUser;

    @NotNull
    @Column(name = "uuid_product")
    private String uuidProduct;

    @NotNull
    @Column(name = "price")
    private Double price;

    @NotNull
    @Column(name = "discount")
    private Double discount;

    @Min(1)
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Column(name = "active")
    private Integer active;

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_user", referencedColumnName = "uuid_user", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_product", referencedColumnName = "uuid_product", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;
}
