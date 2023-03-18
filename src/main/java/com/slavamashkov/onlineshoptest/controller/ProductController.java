package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/info/{id}")
    public String editProduct(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "product-info";
    }

    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable(name = "id") Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Product product = productService.getProductById(id);

        if (user.getBalance() >= product.getPrice() && product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            user.setBalance(user.getBalance() - product.getPrice());

            userService.save(user);
            productService.saveProduct(product);
        }

        return "redirect:/";
    }

    @GetMapping("/add")
    public String openAddProductPage(Model model) {
        Product emptyProduct = new Product();

        model.addAttribute("product", emptyProduct);

        return "add-product";
    }

    @PostMapping("/add")
    public String addNewProduct(@ModelAttribute(name = "product") Product product) {
        productService.saveProduct(product);

        return "redirect:/";
    }
}
