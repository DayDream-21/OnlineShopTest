package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.User;

import java.util.List;
import java.util.Map;

public interface ProductService {
     List<Product> getAllProducts();

     Product getProductById(Long id);

     void saveProduct(Product product);

     //void deleteProduct(Product product);

     void buyProduct(User user, Product product);
}
