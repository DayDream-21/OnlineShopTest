package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.*;
import com.slavamashkov.onlineshoptest.repository.RatingRepository;
import com.slavamashkov.onlineshoptest.repository.ReviewRepository;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.PurchaseService;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping("/info/{id}")
    public String openInfoProductPage(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getProductById(id);

        Double rating = product.getRatings().stream()
                .mapToDouble(Rating::getScore)
                .average()
                .orElse(0.0);

        List<Review> reviews = product.getReviews();

        model.addAttribute("product", product);
        model.addAttribute("rating", rating);
        model.addAttribute("reviews", reviews);

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

    @GetMapping("/rate")
    public String openRateProductsPage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Set<Product> products = user.getPurchaseHistory().stream().map(Purchase::getProduct).collect(Collectors.toSet());

        model.addAttribute("products", products);

        return "product-history";
    }

    @GetMapping("/rate/{id}")
    public String openRateSingleProductPage(@PathVariable(name = "id") Long id, Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Product product = productService.getProductById(id);

        // Проверка на наличие продукта среди покупок пользователя
        if (user.getPurchaseHistory().stream()
                .map(Purchase::getProduct)
                .noneMatch(p -> p.equals(product))) {
            return "redirect:/";
        }

        model.addAttribute("product", product);
        model.addAttribute("rating", new Rating());
        model.addAttribute("review", new Review());

        return "rate-single-product";
    }

    @PostMapping("/rate/{id}")
    public String rateProduct(
            @PathVariable(name = "id") Long id,
            @ModelAttribute(name = "rating") Rating rating,
            @ModelAttribute(name = "review") Review review,
            Authentication authentication
    ) {
        User user = userService.getUserByUsername(authentication.getName());
        Product product = productService.getProductById(id);
        // Проверка на наличие продукта среди покупок пользователя
        if (user.getPurchaseHistory().stream()
                .map(Purchase::getProduct)
                .noneMatch(p -> p.equals(product))) {
            return "redirect:/";
        }

        Rating newRating = Rating.builder()
                .score(rating.getScore())
                .user(user)
                .product(product)
                .build();

        Review newReview = Review.builder()
                .comment(review.getComment())
                .user(user)
                .product(product)
                .build();

        ratingRepository.save(newRating);
        reviewRepository.save(newReview);

        return "redirect:/";
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
