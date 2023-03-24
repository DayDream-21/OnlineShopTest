package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.*;
import com.slavamashkov.onlineshoptest.repository.RatingRepository;
import com.slavamashkov.onlineshoptest.repository.ReviewRepository;
import com.slavamashkov.onlineshoptest.repository.RoleRepository;
import com.slavamashkov.onlineshoptest.service.*;
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
    private final OrganizationService organizationService;
    private final TagService tagService;

    private final RatingRepository ratingRepository;
    private final RoleRepository roleRepository;
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
        productService.save(product);

        return "redirect:/";
    }

    @GetMapping("/return/{id}")
    public String returnProduct(@PathVariable(name = "id") Long id) {
        Purchase purchase = purchaseService.getPurchaseById(id);

        purchaseService.abortPurchase(purchase);

        return "redirect:/history";
    }

    @GetMapping("/rate")
    public String openRateProductsPage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Set<Product> products = user.getPurchaseHistory().stream().map(Purchase::getProduct).collect(Collectors.toSet());

        model.addAttribute("products", products);

        return "product-history";
    }

    @GetMapping("/rate/{id}")
    public String openRateSingleProductPage(
            @PathVariable(name = "id") Long id,
            Model model,
            Authentication authentication) {
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

    @GetMapping("/add")
    public String openAddProductFromAdminPage(Model model) {
        Product emptyProduct = new Product();
        List<Tag> allTags = tagService.getAllTags();

        model.addAttribute("allTags", allTags);
        model.addAttribute("product", emptyProduct);

        return "add-product";
    }

    @GetMapping("/update/{id}")
    public String openUpdateProductFromAdminPage(
            @PathVariable(name = "id") Long id,
            Model model
    ) {
        Product product = productService.getProductById(id);
        List<Tag> allTags = tagService.getAllTags();

        model.addAttribute("allTags", allTags);
        model.addAttribute("product", product);

        return "add-product";
    }

    @PostMapping("/add")
    public String addUpdateProduct(
            Authentication authentication,
            @ModelAttribute(name = "product") Product product,
            @RequestParam(value = "tags", required = false) List<Long> tagsID,
            @RequestParam(value = "organization", required = false) Long orgID
    ) {
        if (tagsID != null) {
            Set<Tag> tags = tagService.getAllTagsByIds(tagsID);
            product.setTags(tags);
        }

        User user = userService.getUserByUsername(authentication.getName());

        if (orgID != null) {
            Organization organization = organizationService.getOrganizationById(orgID);
            product.setOrganization(organization);
        }

        if (user.getRoles().contains(roleRepository.findByName("ROLE_USER")) && product.getOrganization() == null) {
            System.out.println("Пользователь обязан указать организацию");
            return "redirect:/";
        }

        productService.save(product);

        return "redirect:/";
    }

    /*@GetMapping("/delete/{id}")
    public String deleteProduct(@ModelAttribute(name = "product") Product product) {
        productService.deleteProduct(product);

        return "redirect:/";
    }*/
}
