package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public String editProduct(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "product-info";
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
