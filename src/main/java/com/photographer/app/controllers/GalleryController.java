package com.photographer.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class GalleryController {


    @GetMapping("/gallery")
    public String mainGallery(Model model) {
        model.addAttribute("title", "Галерея");
        return "gallery";
    }

}