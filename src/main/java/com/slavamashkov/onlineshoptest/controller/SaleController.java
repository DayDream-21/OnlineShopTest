package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Sale;
import com.slavamashkov.onlineshoptest.entity.Tag;
import com.slavamashkov.onlineshoptest.repository.SaleRepository;
import com.slavamashkov.onlineshoptest.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {
    private final ProductService productService;
    private final SaleRepository saleRepository;

    @GetMapping("/add")
    public String openAddSalePage(Model model) {
        Sale sale = new Sale();
        List<Product> products = productService.getAllProducts();
        Map<Product, Set<Tag>> tags = products.stream().collect(Collectors.toMap(product -> product, Product::getTags));

        model.addAttribute("sale", sale);
        model.addAttribute("products", products);
        model.addAttribute("tags", tags);

        return "add-sale";
    }

    @PostMapping("/add")
    public String addSale(@ModelAttribute(name = "sale") Sale sale) {
        saleRepository.save(sale);

        return "redirect:/";
    }
}
