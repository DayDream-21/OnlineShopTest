package com.slavamashkov.onlineshoptest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "organization", schema = "online_shop_schema")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String description;
    @Column(nullable = false) private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    /*@Lob
    private byte[] logo;*/

    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();
}
