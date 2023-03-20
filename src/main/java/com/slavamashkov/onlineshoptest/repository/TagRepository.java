package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}