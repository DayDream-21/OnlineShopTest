package com.slavamashkov.onlineshoptest.service.implementation;

import com.slavamashkov.onlineshoptest.entity.Sale;
import com.slavamashkov.onlineshoptest.repository.SaleRepository;
import com.slavamashkov.onlineshoptest.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;

    @Override
    public void save(Sale sale) {
        saleRepository.save(sale);
    }
}
