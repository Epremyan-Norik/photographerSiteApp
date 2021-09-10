package com.photographer.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        model.addAttribute("visible_login", "invisible");
        model.addAttribute("visible_reg", "invisible");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Обо мне");
        return "about";
    }

    @GetMapping("/linktree")
    public String linktree(Model model) {
        model.addAttribute("title", "Связь");
        return "linktree";
    }


    @GetMapping("/loading-user")
    public String loading(Model model) {
        model.addAttribute("title", "Подождите");
        return "loading-user";
    }

}