package com.photographer.app.controllers;

import com.photographer.app.modelsNew.Product;
import com.photographer.app.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

@Controller
class PriceListController {

    @Autowired
    Repository repository;


    @GetMapping("/price")
    public String getPrice(Model model) {
        model.addAttribute("title", "Услуги");
        List<Product> products = repository.findAllProducts();
        model.addAttribute("products", products);
        return "service-list";
    }

    @RequestMapping(value="/check", method = RequestMethod.GET)
    public @ResponseBody Integer getShopInJSON() {

        int num = 0;
        final Random random = new Random();
        num = Math.abs(random.nextInt());
        System.out.println(String.format("Number %d :", num ));;
        return num;

    }


    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("title", "Корзина");
        return "cart";
    }

}