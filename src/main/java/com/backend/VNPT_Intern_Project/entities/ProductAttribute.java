package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "product_attribute")

public class ProductAttribute {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_product_attribute", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidProductAttribute;

    @NotNull
    @Column(name = "uuid_product")
    private String uuidProduct;

    @NotNull
    @Column(name = "uuid_attribute")
    private String uuidAttribute;

    @NotNull
    @Column(name = "value")
    private String value;

}
