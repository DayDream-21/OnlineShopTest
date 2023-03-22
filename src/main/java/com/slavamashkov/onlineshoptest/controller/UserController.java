package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.service.PurchaseService;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PurchaseService purchaseService;

    @GetMapping()
    public String openUsersPage(Model model) {
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("users", allUsers);

        return "users";
    }

    @GetMapping("/{id}/history")
    public String openUserHistoryPage(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("purchases", purchaseService.getAllPurchasesByUser(user));

        return "purchase-history";
    }

    @GetMapping("/{id}/edit")
    public String openUserEditPage(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        return "edit-user";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);

        userService.delete(user);

        return "redirect:/users";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute(name = "user") User user) {
        userService.save(user);

        return "redirect:/users";
    }
}
