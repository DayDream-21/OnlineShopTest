package com.slavamashkov.onlineshoptest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.REMOVE)
    private Set<Product> products;

    @Override
    public String toString() {
        return name;
    }
}
