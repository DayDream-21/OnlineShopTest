package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Organization;
import com.slavamashkov.onlineshoptest.entity.Product;
import com.slavamashkov.onlineshoptest.entity.Tag;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.RoleRepository;
import com.slavamashkov.onlineshoptest.service.OrganizationService;
import com.slavamashkov.onlineshoptest.service.ProductService;
import com.slavamashkov.onlineshoptest.service.TagService;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {
    private final UserService userService;
    private final OrganizationService organizationService;
    private final RoleRepository roleRepository;
    private final TagService tagService;
    private final ProductService productService;

    @GetMapping
    public String openOrganizationsPage(
            Model model,
            Authentication authentication
    ) {
        User user = userService.getUserByUsername(authentication.getName());
        List<Organization> organizations;

        if (user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            organizations = organizationService.getAll();
        } else {
            organizations = organizationService.getAllOrganizationsByUser(user);
        }

        model.addAttribute("user", user);
        model.addAttribute("organizations", organizations);

        return "organization";
    }

    @GetMapping("/request/{id}")
    public String openSendRequestOrganizationPage(
            Model model,
            @PathVariable(name = "id") Long userID
    ) {
        User user = userService.getUserById(userID);
        Organization organization = new Organization();
        organization.setUser(user);

        model.addAttribute("organization", organization);

        return "add-organization";
    }

    @GetMapping("/edit/{id}")
    public String openEditOrganizationPage(
            Model model,
            @PathVariable(name = "id") Long orgID
    ) {
        Organization organization = organizationService.getOrganizationById(orgID);

        model.addAttribute("organization", organization);

        return "add-organization";
    }

    @PostMapping("/add")
    public String addOrganization(
            @ModelAttribute(name = "organization") Organization organization
            //@RequestParam(name = "logo", required = true) MultipartFile logoFile
    ) {
        //organization.setLogo(logoFile.getBytes());
        //organization.setActive(false);

        organizationService.save(organization);

        return "redirect:/organization";
    }

    @GetMapping("/addProduct")
    public String openAddProductFromOrganizationPage(
            Model model,
            Authentication authentication
    ) {
        User user = userService.getUserByUsername(authentication.getName());
        Product emptyProduct = new Product();

        List<Organization> organizations = organizationService.getAllOrganizationsByUserAndActiveIsTrue(user);
        List<Tag> allTags = tagService.getAllTags();

        model.addAttribute("allTags", allTags);
        model.addAttribute("product", emptyProduct);
        model.addAttribute("organizations", organizations);

        return "add-product";

    }

    @GetMapping("/updateProduct/{id}")
    public String openEditProductFromOrganizationPage(
            @PathVariable(name = "id") Long productID,
            Model model,
            Authentication authentication
    ) {
        User user = userService.getUserByUsername(authentication.getName());
        Product product = productService.getProductById(productID);

        List<Organization> organizations = organizationService.getAllOrganizationsByUserAndActiveIsTrue(user);
        List<Tag> allTags = tagService.getAllTags();

        model.addAttribute("allTags", allTags);
        model.addAttribute("product", product);
        model.addAttribute("organizations", organizations);

        return "add-product";
    }

    /*@GetMapping("/image/{id}")
    public void showProductImage(
            @PathVariable(name = "id") Long id,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("image/png");

        Organization organization = organizationService.getOrganizationById(id);

        InputStream is = new ByteArrayInputStream(organization.getLogo());
        IOUtils.copy(is, response.getOutputStream());
    }*/
}
