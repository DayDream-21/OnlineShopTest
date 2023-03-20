package com.slavamashkov.onlineshoptest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ManyToMany
    @JoinTable(
            name = "product_sale",
            schema = "online_shop_schema",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sale_id")
    )
    @ToString.Exclude
    private Set<Sale> sales;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Rating> ratings;

    @ManyToMany
    @JoinTable(
            name = "product_tag",
            schema = "online_shop_schema",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ToString.Exclude
    private Set<Tag> tags;
}


