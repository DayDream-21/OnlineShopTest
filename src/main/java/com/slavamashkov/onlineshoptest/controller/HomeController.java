package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final ProductService productService;

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        return "home";
    }
}
