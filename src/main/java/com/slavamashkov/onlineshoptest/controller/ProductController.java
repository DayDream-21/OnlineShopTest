package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Purchase;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.PurchaseService;
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
    private final PurchaseService purchaseService;

    @GetMapping("/info/{id}")
    public String openInfoProductPage(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "product-info";
    }

    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable(name = "id") Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Product product = productService.getProductById(id);

        productService.buyProduct(user, product);

        userService.save(user);
        productService.saveProduct(product);

        return "redirect:/";
    }

    @GetMapping("/return/{id}")
    public String returnProduct(@PathVariable(name = "id") Long id) {
        Purchase purchase = purchaseService.getPurchaseById(id);

        purchaseService.abortPurchase(purchase);

        return "redirect:/history";
    }

    @GetMapping("/add")
    public String openAddProductPage(Model model) {
        Product emptyProduct = new Product();

        model.addAttribute("product", emptyProduct);

        return "add-product";
    }

    @GetMapping("/update/{id}")
    public String openUpdateProductPage(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "add-product";
    }

    @PostMapping("/add")
    public String addNewProduct(@ModelAttribute(name = "product") Product product) {
        productService.saveProduct(product);

        return "redirect:/";
    }

    /*@GetMapping("/delete/{id}")
    public String deleteProduct(@ModelAttribute(name = "product") Product product) {
        productService.deleteProduct(product);

        return "redirect:/";
    }*/
}
