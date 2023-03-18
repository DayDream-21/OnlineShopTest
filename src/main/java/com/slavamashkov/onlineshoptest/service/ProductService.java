package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
     List<Product> getAllProducts();

     Product getProductById(Long id);

     void saveProduct(Product product);
}
