package com.backend.VNPT_Intern_Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_order", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidOrder;

    @Column(name = "uuid_user", insertable = false, updatable = false)
    private String uuidUser;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "token")
    private String token;

    @Column(name = "status")
    private Integer status;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "item_discount")
    private Double itemDiscount;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "shipping")
    private Double shipping;

    @Column(name = "total")
    private Double total;

    @Column(name = "promo")
    private String promo;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "grand_total")
    private Double grandTotal;

    @Column(name = "phone")
    private String phone;

    @Column(name = "payment_methods")
    private String paymentMethods;

    @Column(name = "note")
    private String note;

    @Column(name = "address_ship")
    private String addressShip;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_user")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderItem> orderItemList;

}
