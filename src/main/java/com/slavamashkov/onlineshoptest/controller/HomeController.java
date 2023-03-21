package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.*;
import com.slavamashkov.onlineshoptest.repository.UserRepository;
import com.slavamashkov.onlineshoptest.service.NotificationService;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.PurchaseService;
import com.slavamashkov.onlineshoptest.service.UserService;
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
        List<Product> allProducts = productService.getAllProducts();

        Map<Product, Double> avgRatings = getProductAvgRatingMap(allProducts);

        Map<Product, Set<Tag>> tags = allProducts.stream()
                .collect(Collectors.toMap(product -> product, Product::getTags));

        model.addAttribute("ratings", avgRatings);
        model.addAttribute("balance", user.getBalance());
        model.addAttribute("products", allProducts);
        model.addAttribute("tags", tags);

        return "home";
    }

    @GetMapping("/history")
    public String openPurchaseHistoryPage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());

        model.addAttribute("purchases", purchaseService.getAllPurchasesByUser(user));

        return "purchase-history";
    }

    @GetMapping("/notifications")
    public String openSendNotificationPage(Model model) {
        Notification notification = new Notification();
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("notification", notification);
        model.addAttribute("allUsers", allUsers);

        return "send-notification";
    }

    @GetMapping("/notifications/inspect")
    public String openSendNotificationPage(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Set<Notification> notifications = notificationService.getNotificationsByUser(user);

        model.addAttribute("notifications", notifications);

        return "notifications";
    }

    @PostMapping("/notification/send")
    public String sendNotification(
            @ModelAttribute(name = "notification") Notification notification,
            @RequestParam("users") List<Long> userIds
    ) {
        List<User> users = userRepository.findAllById(userIds);

        notification.setUsers(new HashSet<>(users));
        notification.setDateTime(LocalDateTime.now());

        notificationService.save(notification);

        users.forEach(user -> user.getNotifications().add(notification));
        users.forEach(userService::save);

        return "redirect:/";
    }

    @GetMapping("/users")
    public String openUsersPage(Model model) {
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("users", allUsers);

        return "users";
    }

    @GetMapping("/users/{id}/history")
    public String openUserHistoryPage(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("purchases", purchaseService.getAllPurchasesByUser(user));

        return "purchase-history";
    }

    @GetMapping("/users/{id}/edit")
    public String openUserEditPage(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        return "edit-user";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);

        userService.delete(user);

        return "redirect:/users";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute(name = "user") User user) {
        userService.save(user);

        return "redirect:/users";
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
