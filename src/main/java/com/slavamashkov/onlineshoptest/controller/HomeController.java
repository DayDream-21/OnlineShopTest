package com.slavamashkov.onlineshoptest.controller;

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

        model.addAttribute("balance", user.getBalance());
        model.addAttribute("products", productService.getAllProducts());

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
