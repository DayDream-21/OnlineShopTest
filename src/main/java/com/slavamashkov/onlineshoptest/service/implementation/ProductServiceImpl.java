package com.slavamashkov.onlineshoptest.service.implementation;

import com.slavamashkov.onlineshoptest.entity.*;
import com.slavamashkov.onlineshoptest.repository.ProductRepository;
import com.slavamashkov.onlineshoptest.repository.PurchaseRepository;
import com.slavamashkov.onlineshoptest.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.findAllByActiveIsTrue();
    }

    @Override
    public List<Product> getUnactiveProducts() {
        return productRepository.findAllByActiveIsFalse();
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
            user.setBalance(user.getBalance() - priceWithSale(product));
            // Если продукт продавался от лица организации, к которой привязан пользователь,
            // то продавец должен получить прибыль за вычетом комиссии
            if (product.getOrganization() != null) {
                User seller = userRepository.getUserByOrganizations(product.getOrganization());

                seller.setBalance(seller.getBalance() + priceWithSale(product) * 0.95);

                userRepository.save(seller);
            }

            Purchase purchase = new Purchase();

            purchase.setUser(user);
            purchase.setProduct(product);
            purchase.setPurchaseTime(LocalDateTime.now());

            user.getPurchaseHistory().add(purchase);

            purchaseRepository.save(purchase);
        }
    }

    public static Double priceWithSale(Product product) {
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
