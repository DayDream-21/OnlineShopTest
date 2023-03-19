package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Purchase;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    /*@Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }*/

    @Override
    public void buyProduct(User user, Product product) {
        if (user.getBalance() >= product.getPrice() && product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            user.setBalance(user.getBalance() - product.getPrice());

            Purchase purchase = new Purchase();

            purchase.setUser(user);
            purchase.setProduct(product);
            purchase.setPurchaseTime(LocalDateTime.now());

            user.getPurchaseHistory().add(purchase);
        }
    }
}
