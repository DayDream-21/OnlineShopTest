package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Organization;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.RoleRepository;
import com.slavamashkov.onlineshoptest.service.OrganizationService;
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
