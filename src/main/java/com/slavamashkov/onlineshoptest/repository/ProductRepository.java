package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> findAllByTagsIn(Set<Tag> tags);
    List<Product> findAllByActiveIsFalse();
    List<Product> findAllByActiveIsTrue();
}
