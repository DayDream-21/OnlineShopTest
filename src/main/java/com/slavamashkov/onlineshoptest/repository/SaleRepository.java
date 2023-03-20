package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}