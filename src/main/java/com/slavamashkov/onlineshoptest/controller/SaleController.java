package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Sale;
import com.slavamashkov.onlineshoptest.entity.Tag;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.SaleService;
import com.slavamashkov.onlineshoptest.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {
    private final ProductService productService;
    private final TagService tagService;
    private final SaleService saleService;

    @GetMapping("/add")
    public String openAddSalePage(Model model) {
        Sale sale = new Sale();
        List<Product> products = productService.getAllProducts();
        List<Tag> tags = tagService.getAllTags();

        model.addAttribute("sale", sale);
        model.addAttribute("products", products);
        model.addAttribute("tags", tags);

        return "add-sale";
    }

    @Transactional
    @PostMapping("/add")
    public String addSale(
            @RequestParam(value = "tags", required = false) List<Long> tagsID,
            @RequestParam(value = "products", required = false) List<Long> productsID,
            @ModelAttribute(name = "sale") Sale sale
    ) {
        Set<Product> allProductsByTags = new HashSet<>();
        Set<Product> allProductsByIds = new HashSet<>();

        if (tagsID != null) {
            allProductsByTags.addAll(productService.getAllProductsByTags(new HashSet<>(tagService.getAllTagsByIds(tagsID))));
        }

        if (productsID != null) {
            allProductsByIds.addAll(productService.getAllProductsByIds(productsID));
        }

        allProductsByIds.addAll(allProductsByTags);

        sale.setProducts(allProductsByIds);

        saleService.save(sale);

        // При упрощении записи возникает ConcurrentModificationException
        List<Product> productList = new ArrayList<>(allProductsByIds);
        for (Iterator<Product> iterator = productList.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            product.getSales().add(sale);
            productService.save(product);
        }

        return "redirect:/";
    }
}
