package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Rating;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.PurchaseService;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final ProductService productService;
    private final UserService userService;
    private final PurchaseService purchaseService;

    @GetMapping()
    public String openHomePage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());


        List<Product> allProducts = productService.getAllProducts();
        Map<Product, Double> avgRatings = allProducts.stream().collect(Collectors.toMap(product -> product, product -> {
            List<Rating> ratings = product.getRatings();
            if (ratings.isEmpty()) {
                return 0.0;
            }

            return ratings.stream()
                    .mapToDouble(Rating::getScore)
                    .average()
                    .getAsDouble();
        }));

        model.addAttribute("ratings", avgRatings);
        model.addAttribute("balance", user.getBalance());
        model.addAttribute("products", allProducts);

        return "home";
    }

    @GetMapping("/history")
    public String openPurchaseHistoryPage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());

        model.addAttribute("purchases", purchaseService.getAllPurchasesByUser(user));

        return "purchase-history";
    }

    @GetMapping("/users")
    public String openUsersPage(Model model) {
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("users", allUsers);

        return "users";
    }

    @GetMapping("/users/user/{id}/history")
    public String openUserHistoryPage(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("purchases", purchaseService.getAllPurchasesByUser(user));

        return "purchase-history";
    }

    @GetMapping("/users/user/{id}/edit")
    public String openUserEditPage(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        return "edit-user";
    }

    @GetMapping("/users/user/delete")
    public String deleteUser(@ModelAttribute(name = "user") User user) {
        userService.delete(user);

        return "redirect:/users";
    }

    @PostMapping("/users/user/add")
    public String addUser(@ModelAttribute(name = "user") User user) {
        userService.save(user);

        return "redirect:/users";
    }
}
