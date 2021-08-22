package com.photographer.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class DiscountController {


    @GetMapping("/discount")
    public String mainGallery(Model model) {
        model.addAttribute("title", "Специальные предложения");
        return "discounts-offers";
    }

}