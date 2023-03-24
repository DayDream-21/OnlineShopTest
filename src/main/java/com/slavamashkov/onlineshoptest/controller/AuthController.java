package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.RoleRepository;
import com.slavamashkov.onlineshoptest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String successLogin() {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") User user) {
        user.getRoles().add(roleRepository.findByName("ROLE_USER"));
        user.setBalance(0.0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        userService.save(user);

        return "redirect:/login";
    }
}

