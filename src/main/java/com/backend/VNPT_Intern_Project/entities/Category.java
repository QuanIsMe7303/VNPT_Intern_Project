package com.backend.vnptproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid_category", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuidCategory;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "meta_title")
    private String meta_title;

    @NotNull
    @Column(name = "slug")
    private String slug;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;

}
