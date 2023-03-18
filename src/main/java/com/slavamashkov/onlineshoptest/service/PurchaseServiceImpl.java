package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Purchase;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.PurchaseRepository;
import com.slavamashkov.onlineshoptest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Override
    public Set<Purchase> getAllPurchasesByUser(User user) {
        return purchaseRepository.getAllByUser(user);
    }
}
