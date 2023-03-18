package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping()
    public String home(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        model.addAttribute("balance", user.getBalance());
        model.addAttribute("products", productService.getAllProducts());

        return "home";
    }
}
