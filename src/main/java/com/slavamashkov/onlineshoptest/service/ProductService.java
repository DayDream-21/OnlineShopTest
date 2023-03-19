package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Product;

import java.util.List;

public interface ProductService {
     List<Product> getAllProducts();

     Product getProductById(Long id);

     void saveProduct(Product product);

     void deleteProduct(Product product);
}
