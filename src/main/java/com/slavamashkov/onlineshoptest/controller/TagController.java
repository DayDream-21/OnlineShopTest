package com.slavamashkov.onlineshoptest.controller;

import com.slavamashkov.onlineshoptest.entity.Tag;
import com.slavamashkov.onlineshoptest.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @GetMapping("/add")
    public String openAddTagPage(Model model) {
        Tag tag = new Tag();

        model.addAttribute("tag", tag);

        return "add-tag";
    }

    @PostMapping("/add")
    public String addTag(@ModelAttribute(name = "tag") Tag tag) {
        tagService.save(tag);

        return "redirect:/";
    }
}
