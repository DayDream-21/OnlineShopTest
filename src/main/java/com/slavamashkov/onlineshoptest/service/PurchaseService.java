package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Purchase;
import com.slavamashkov.onlineshoptest.entity.User;

import java.util.Set;

public interface PurchaseService {
    Set<Purchase> getAllPurchasesByUser(User user);
}
