package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Tag;
import com.slavamashkov.onlineshoptest.entity.User;

import java.util.List;
import java.util.Set;

public interface ProductService {
     List<Product> getAllProducts();

     Product getProductById(Long id);

     Set<Product> getAllProductsByTags(Set<Tag> tags);

     void save(Product product);

     //void deleteProduct(Product product);

     void buyProduct(User user, Product product);

     Set<Product> getAllProductsByIds(List<Long> productsID);
}
