package com.slavamashkov.onlineshoptest.service.implementation;

import com.slavamashkov.onlineshoptest.entity.*;
import com.slavamashkov.onlineshoptest.repository.ProductRepository;
import com.slavamashkov.onlineshoptest.repository.PurchaseRepository;
import com.slavamashkov.onlineshoptest.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Set<Product> getAllProductsByTags(Set<Tag> tags) {
        return productRepository.findAllByTagsIn(tags);
    }

    @Override
    public void save(Product product) {
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
            product.setPrice(priceWithSale(product));
            user.setBalance(user.getBalance() - product.getPrice());

            Purchase purchase = new Purchase();

            purchase.setUser(user);
            purchase.setProduct(product);
            purchase.setPurchaseTime(LocalDateTime.now());

            user.getPurchaseHistory().add(purchase);

            purchaseRepository.save(purchase);
        }
    }

    private Double priceWithSale(Product product) {
        Set<Sale> sales = product.getSales();
        double maxDiscount = sales.stream()
                .filter(sale -> sale.getDate_from().isBefore(LocalDateTime.now())
                        && sale.getDate_to().isAfter(LocalDateTime.now()))
                .mapToDouble(Sale::getSaleAmount)
                .max()
                .orElse(0);
        return product.getPrice() * (100 - maxDiscount) / 100;
    }

    @Override
    public Set<Product> getAllProductsByIds(List<Long> productsID) {
        return new HashSet<>(productRepository.findAllById(productsID));
    }
}
