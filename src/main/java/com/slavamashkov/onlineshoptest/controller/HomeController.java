package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.*;
import com.slavamashkov.onlineshoptest.repository.UserRepository;
import com.slavamashkov.onlineshoptest.service.NotificationService;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.PurchaseService;
import com.slavamashkov.onlineshoptest.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final ProductService productService;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @GetMapping()
    public String openHomePage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        List<Product> activeProducts = productService.getActiveProducts();
        List<Product> inactiveProducts = productService.getUnactiveProducts();

        List<Product> productsWithDiscount = activeProducts.stream()
                .peek(product -> {
                    Set<Sale> sales = product.getSales();
                    double maxDiscount = sales.stream()
                            .filter(sale -> sale.getDate_from().isBefore(LocalDateTime.now())
                                    && sale.getDate_to().isAfter(LocalDateTime.now()))
                            .mapToDouble(Sale::getSaleAmount)
                            .max()
                            .orElse(0);
                    double newPrice = product.getPrice() * (100 - maxDiscount) / 100;
                    product.setPrice(newPrice);
                })
                .toList();


        Map<Product, Double> avgRatings = getProductAvgRatingMap(activeProducts);

        Map<Product, Set<Tag>> tags = activeProducts.stream()
                .collect(Collectors.toMap(product -> product, Product::getTags));

        model.addAttribute("ratings", avgRatings);
        model.addAttribute("balance", user.getBalance());
        model.addAttribute("activeProducts", productsWithDiscount);
        model.addAttribute("inactiveProducts", inactiveProducts);
        model.addAttribute("tags", tags);

        return "home";
    }

    @GetMapping("/history")
    public String openPurchaseHistoryPage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());

        model.addAttribute("purchases", purchaseService.getAllPurchasesByUser(user));

        return "purchase-history";
    }

    @GetMapping("/notification")
    public String openSendNotificationPage(Model model) {
        Notification notification = new Notification();
        List<User> users = userService.getAllUsers();

        model.addAttribute("notification", notification);
        model.addAttribute("users", users);

        return "notification";
    }

    @Transactional
    @PostMapping("/notification/send")
    public String sendNotification(
            @ModelAttribute(name = "notification") Notification notification,
            @RequestParam("users") List<Long> usersID
    ) {
        List<User> users = userRepository.findAllById(usersID);

        notification.setUsers(new HashSet<>(users));
        notification.setDateTime(LocalDateTime.now());

        notificationService.save(notification);

        users.forEach(user -> user.getNotifications().add(notification));
        users.forEach(userService::save);

        return "redirect:/";
    }

    @GetMapping("/notifications/inspect")
    public String openSendNotificationPage(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Set<Notification> notifications = notificationService.getNotificationsByUser(user);

        model.addAttribute("notifications", notifications);

        return "notifications";
    }

    private static Map<Product, Double> getProductAvgRatingMap(List<Product> allProducts) {
        return allProducts.stream()
                .collect(Collectors.toMap(product -> product, product -> {
                    List<Rating> ratings = product.getRatings();
                    if (ratings.isEmpty()) {
                        return 0.0;
                    }

                    return ratings.stream()
                            .mapToDouble(Rating::getScore)
                            .average()
                            .orElse(0.0);
                }));
    }
}
