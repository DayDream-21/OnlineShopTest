package com.slavamashkov.onlineshoptest.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products", schema = "online_shop_schema")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) private String name;
    @Column(nullable = true) private String description;
    @Column(nullable = false) private Double price;
    @Column(nullable = false) private Integer quantity;
}


