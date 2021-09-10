package com.photographer.app.controllers;

import com.photographer.app.modelsNew.CartItem;
import com.photographer.app.modelsNew.CartItemForUI;
import com.photographer.app.modelsNew.Product;
import com.photographer.app.modelsNew.User;
import com.photographer.app.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @RequestMapping(value="/getGuestId", method = RequestMethod.GET)
    public @ResponseBody Integer getGuestId() {

        return repository.generateGuestId();
    }


    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("title", "Корзина");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = repository.findUserByUsername(currentPrincipalName);
        List<CartItem> cartItems = repository.getCartItemByUserId(user.getId());
        List<Product> products = repository.findAllProducts();
        List<CartItemForUI> resultCart = new ArrayList<>();
        for(CartItem cartItem : cartItems) {
            for(Product product : products){
                if((product.getId()==cartItem.getPr_id())& product.isActive()){
                    resultCart.add(new CartItemForUI(cartItem.getId(),
                             product.getName(),
                             product.getPrice(),
                             cartItem.getCounter()));

                }
            }
        }
        model.addAttribute("cart", resultCart);
        return "cart";
    }


    @RequestMapping(value="/transferGuest",method = RequestMethod.POST)
    @ResponseBody
    public String transferCartGuestToUser(@RequestBody String guestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = repository.findUserByUsername(currentPrincipalName);
        guestId = guestId.replaceAll("[^\\d.]", "");
        long guestIdInt = new Long(guestId);
        List<CartItem> cartItems = repository.getCartItemByGuestId(guestIdInt);
        for(CartItem cartItem : cartItems){
            cartItem.setEn_id(user.getId());
            repository.updateCartItem(cartItem);
        }
        return guestId;
    }



}