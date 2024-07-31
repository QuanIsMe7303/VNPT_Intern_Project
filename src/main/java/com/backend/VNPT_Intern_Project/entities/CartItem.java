package com.backend.VNPT_Intern_Project.entities;

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

    @Column(name = "uuid_cart")
    @NotNull
    private String uuidCart;

    @Column(name = "uuid_product")
    @NotNull
    private String uuidProduct;

    @Column(name = "price")
    @NotNull
    private Double price;

    @Column(name = "discount")
    @NotNull
    private Double discount;

    @Column(name = "quantity")
    @Min(1)
    @NotNull
    private Integer quantity;

    @Column(name = "active")
    @NotNull
    private Integer active;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_cart", referencedColumnName = "uuid_cart", insertable = false, updatable = false)
    private User user;
}
