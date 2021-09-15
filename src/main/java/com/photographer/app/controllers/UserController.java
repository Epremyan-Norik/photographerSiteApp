package com.photographer.app.controllers;

import com.photographer.app.model.Image;
import com.photographer.app.model.Order;
import com.photographer.app.model.User;
import com.photographer.app.repo.Repository;
import com.photographer.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService service;
    @Autowired
    Repository repository;


    @RequestMapping(value="/mailConfirm", method = RequestMethod.POST)
    public @ResponseBody
    Integer getGuestId(@AuthenticationPrincipal User user, @RequestBody String email) {

        String subEmail = email.substring(1,email.length()-1);
        subEmail =subEmail.replaceAll("\\s+","");
        service.addEmail(subEmail, user);
        return 1;
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model){
        List<Image> userphoto = repository.getImagesByEnId(user.getId());
        if(!userphoto.isEmpty()){
            model.addAttribute("image", userphoto.get(0).getImagebyte64());
        }
        model.addAttribute("username", user.getUsername());

        List<Order> orders = repository.getOrdersByUserId(user.getId());
        orders.forEach(el->{
            String data = el.getOrder_dt().substring(0,11);
            el.setOrder_dt(data);
        });

        if(service.emailIsConfirmed(user)){
            model.addAttribute("confirmed","(Подтверждён) Email:");
        }
        else{
            model.addAttribute("confirmed","(НЕ Подтверждён) Email:");
        }
        model.addAttribute("orders", orders);

        model.addAttribute("email", service.getUserEmail(user));

        System.out.println(service.getUserEmail(user));


        return "profile";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code, @AuthenticationPrincipal User user){
        model.addAttribute("title", "Активация");
        if(service.confirmEmail(user, code)){
            model.addAttribute("result", "Активация прошла успешно");
        } else {
            model.addAttribute("result", "Неверный код активации");
        }

        return "mailConfirm";
    }

}
