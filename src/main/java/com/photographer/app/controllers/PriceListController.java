package com.photographer.app.controllers;

import com.photographer.app.model.*;
import com.photographer.app.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

@Controller
class PriceListController {

    @Autowired
    Repository repository;


    @GetMapping("/price")
    public String getPrice(Model model) {
        model.addAttribute("title", "Услуги");
        List<Product> products = repository.findAllProducts();
        for(Product product : products){
            product.setImage(repository.getImagesByEnId(product.getId()).get(0).getImagebyte64());
        }
        model.addAttribute("products", products);
        return "service-list";
    }

    @RequestMapping(value="/getGuestId", method = RequestMethod.GET)
    public @ResponseBody Integer getGuestId() {

        return repository.generateGuestId();
    }

    @GetMapping("/createOrder")
    public String createOrder(Model model) {
        model.addAttribute("title", "Заказ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = repository.findUserByUsername(currentPrincipalName);
        if (repository.createOrder(user)){
            model.addAttribute("result", "Заказ успешно создан");
        } else{
            model.addAttribute("result", "Ошибка создания заказа");
        }
        return "createOrder";
    }

    @GetMapping("/order/{id}")
    public String getOrder(@AuthenticationPrincipal User user, @PathVariable(value = "id") long id,  Model model) {
        model.addAttribute("title", "Заказ "+String.valueOf(id));

        Order order  = repository.getOrderById(id);
        if(order==null){
            return "order";
        }
        if(order.getUser_id()!= user.getId()){
            return "order";
        }

        List<OrderItem> orderItems = repository.getAllOrderItemsByOrderID(order.getId());
        List<CartItemForUI> resultCart = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            resultCart.add(new CartItemForUI(0,
                    repository.findProductById(orderItem.getPr_id()).getName(),
                    repository.findProductById(orderItem.getPr_id()).getPrice(),
                    orderItem.getCount()));
        }
        model.addAttribute("order", order);
        model.addAttribute("orderItems", resultCart);

        /*if (repository.createOrder(user)){
            model.addAttribute("result", "Заказ успешно создан");
        } else{
            model.addAttribute("result", "Ошибка создания заказа");
        }*/


        return "order";
    }



    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("title", "Корзина");
        return "cart";
    }


    @RequestMapping(value="/getCart",method = RequestMethod.POST)
    public @ResponseBody List<CartItemForUI> getCart(@RequestBody String guestId) {
        List<CartItem> cartItems;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = repository.findUserByUsername(currentPrincipalName);
        if(user==null){
            guestId = guestId.replaceAll("[^\\d.]", "");
            long guestIdInt = new Long(guestId);
            cartItems = repository.getCartItemByGuestId(guestIdInt);
        }
        else{
            cartItems = repository.getCartItemByUserId(user.getId());
        }
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
        return resultCart;
    }

    @RequestMapping(value="/addToCart/{id}",method = RequestMethod.POST)
    public @ResponseBody boolean addToCart(@PathVariable(value = "id") int productId, @RequestBody String guestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = repository.findUserByUsername(currentPrincipalName);

        CartItem cartItem = new CartItem();
        cartItem.setPr_id(productId);
        cartItem.setCounter(1);

        if(user==null){
            guestId = guestId.replaceAll("[^\\d.]", "");
            long guestIdInt = new Long(guestId);
            cartItem.setGuest_id(guestIdInt);
            cartItem.setEn_id(-1);
        }
        else{
            cartItem.setEn_id(user.getId());
        }
        repository.insertCartItem(cartItem);
        return true;
    }

    @RequestMapping(value = "/deleteCartItem/{id}", method = RequestMethod.POST)
    public @ResponseBody
    boolean deleteCartItem(@PathVariable(value = "id") int cartItemId, @RequestBody String guestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = repository.findUserByUsername(currentPrincipalName);

        CartItem cartItem = repository.getCartItemById(cartItemId);

        if (user == null) {
            guestId = guestId.replaceAll("[^\\d.]", "");
            long guestIdInt = new Long(guestId);
            if (cartItem.getGuest_id() == guestIdInt) {
                repository.deleteCartItemById(cartItemId);
            }
        } else {
            if (user.getId() == cartItem.getEn_id()) {
                repository.deleteCartItemById(cartItemId);
            }
        }
        return true;
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