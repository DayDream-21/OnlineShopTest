package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.Purchase;
import com.slavamashkov.onlineshoptest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Set<Purchase> getAllByUser(User user);
}
