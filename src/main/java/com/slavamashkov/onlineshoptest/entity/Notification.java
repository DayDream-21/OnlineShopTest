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
@Table(name = "notifications", schema = "online_shop_schema")
public class Notification {
    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) private String header;
    @Column(nullable = false) private LocalDateTime dateTime;
    @Column(nullable = false) private String text;

    @ManyToMany(mappedBy = "notifications", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<User> users;
}
