package com.backend.VNPT_Intern_Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class OrderItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_order_item", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidOrderItem;

    @Column(name = "uuid_product", insertable = false, updatable = false)
    private String uuidProduct;

    @Column(name = "uuid_order", insertable = false, updatable = false)
    private String uuidOrder;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Column(name = "content")
    private String content;

    @ManyToOne()
    @JoinColumn(name = "uuid_product")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "uuid_order")
    @JsonIgnoreProperties("orderItemList")
    private Order order;

}
