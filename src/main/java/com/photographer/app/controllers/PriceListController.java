package com.photographer.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class PriceListController {


    @GetMapping("/price")
    public String mainGallery(Model model) {
        model.addAttribute("title", "Услуги");
        return "service-list";
    }

}