package com.slavamashkov.onlineshoptest.service.implementation;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Purchase;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.ProductRepository;
import com.slavamashkov.onlineshoptest.repository.PurchaseRepository;
import com.slavamashkov.onlineshoptest.repository.UserRepository;
import com.slavamashkov.onlineshoptest.service.PurchaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Set<Purchase> getAllPurchasesByUser(User user) {
        return purchaseRepository.getAllByUser(user);
    }

    @Override
    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.getPurchaseById(id);
    }

    @Override
    @Transactional
    public void abortPurchase(Purchase purchase) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime purchaseTime = purchase.getPurchaseTime();

        if (Duration.between(purchaseTime, currentTime).toSeconds() > (long) (24 * 60 * 60)) {
            return;
        }

        Optional<Product> optionalProduct = productRepository.findById(purchase.getProduct().getId());

        if (optionalProduct.isPresent()) {
            // Возвращаем продукт в базу если продукт с таким id уже находится там
            Product product = optionalProduct.get();
            product.setQuantity(product.getQuantity() + 1);
            // Возвращаем пользователю деньги за покупку
            User user = purchase.getUser();
            user.setBalance(user.getBalance() + ProductServiceImpl.priceWithSale(product));

            // Если продукт продавался от лица организации, к которой привязан пользователь,
            // то продавец должен вернуть деньги обратно в полном объеме
            if (product.getOrganization() != null) {
                User seller = userRepository.getUserByOrganizations(product.getOrganization());

                seller.setBalance(seller.getBalance() - ProductServiceImpl.priceWithSale(product) * 0.95);

                userRepository.save(seller);
            }


            // Удаляем покупку из списка покупок пользователя
            purchaseRepository.deletePurchaseById(purchase.getId());
        } else {
            // Если, продукт купленный пользователем, был удален, то добавляем в базу, продукт из покупки
            Product newProduct = purchase.getProduct();

            newProduct.setQuantity(1);

            // Возвращаем пользователю деньги за покупку
            User user = purchase.getUser();
            user.setBalance(user.getBalance() + newProduct.getPrice());
            // Удаляем покупку из списка покупок пользователя
            purchaseRepository.deletePurchaseById(purchase.getId());

            productRepository.save(newProduct);
        }
    }
}
