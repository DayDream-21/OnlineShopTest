package com.slavamashkov.onlineshoptest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sales", schema = "online_shop_schema")
public class Sale {
    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private Double saleAmount;
    @Column(nullable = false) private LocalDateTime from;
    @Column(nullable = false) private LocalDateTime to;

    @ManyToMany(mappedBy = "sales")
    @ToString.Exclude
    private Set<Product> products;
}
