package com.photographer.app.controllers;

import com.photographer.app.model.*;
import com.photographer.app.repo.Repository;
import com.photographer.app.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    Repository repository;

    @GetMapping("/admin")
    public String userList(Model model) {
        //model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("titile", "Панель Администратора");
        return "admin";
    }

    @GetMapping("/admin/orders")
    public String getOrders(Model model) {
        model.addAttribute("titile", "Заказы пользователей");
        List<Order> orders = repository.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    @GetMapping("/admin/orders/{id}")
    public String orderDetail(Model model, @PathVariable(value = "id") long id) {
        model.addAttribute("title", "Заказ " + String.valueOf(id));

        Order order = repository.getOrderById(id);
        if (order == null) {
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


        return "admin-order-detail";
    }

    @RequestMapping(value = "/admin/changeStatus/{id}", method = RequestMethod.POST)
    public @ResponseBody
    void getGuestId(@PathVariable(value = "id") long id, @RequestBody String status) {
        status = status.substring(1, status.length() - 1);
        switch (status) {
            case "new": {
                status = "Новый";
                break;
            }
            case "inprogress": {
                status = "В работе";
                break;
            }
            case "ended": {
                status = "Завершен";
                break;
            }
        }
        repository.changeOrderStatus(id, status);

    }

    @GetMapping("/admin/products")
    public String getAllProducts(Model model) {
        model.addAttribute("title", "Администрирование товаров");
        List<Product> products = repository.findAllProducts();
        for (Product product : products) {
            product.setImage(repository.getImagesByEnId(product.getId()).get(0).getImagebyte64());
        }
        model.addAttribute("products", products);

        return "admin-products";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String editProduct(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("title", "Изменение товара");
        Product product = repository.findProductById(id);
        product.setImage(repository.getImagesByEnId(product.getId()).get(0).getImagebyte64());
        model.addAttribute("pr", product);

        return "admin-product-edit";
    }

    @RequestMapping(value = "/admin/products/edit/{id}", method = RequestMethod.POST)
    public String editProduct(@PathVariable(value = "id") long id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "price") double price,
                              @RequestParam(name = "active") String active,
                              @RequestParam(name = "image") MultipartFile picture) throws IOException {
        if (!picture.isEmpty()) {
            List<Image> existImage = repository.getImagesByEnId(id);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("data:image/jpeg;base64,");
            stringBuilder.append(StringUtils.newStringUtf8(Base64.encodeBase64(picture.getBytes(), false)));
            if (existImage.isEmpty()) {
                Image image = new Image();
                image.setEn_id(id);
                image.setImagebyte64(stringBuilder.toString());
                image.setId(repository.insertImage(image));
            } else {
                existImage.get(0).setImagebyte64(stringBuilder.toString());
                repository.updateImage(existImage.get(0));

            }
        }
        Product product = repository.findProductById(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setActive(Boolean.parseBoolean(active));
        repository.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/add")
    public String addProductMapp(Model model) {
        model.addAttribute("title", "Добавление товара");
        return "admin-product-add";
    }

    @RequestMapping(value = "/admin/products/add", method = RequestMethod.POST)
    public String addProduct(@RequestParam(name = "name") String name,
                             @RequestParam(name = "description") String description,
                             @RequestParam(name = "price") double price,
                             @RequestParam(name = "active") String active,
                             @RequestParam(name = "image") MultipartFile picture) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data:image/jpeg;base64,");
        stringBuilder.append(StringUtils.newStringUtf8(Base64.encodeBase64(picture.getBytes(), false)));

        Product product = new Product();
        product.setImage(stringBuilder.toString());
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setActive(Boolean.parseBoolean(active));
        repository.saveProduct(product);
        return "redirect:/admin/products";
    }

    @RequestMapping(value = "/admin/products/remove/{id}", method = RequestMethod.GET)
    public String removeProduct(@PathVariable(value = "id") long id) {
        repository.deleteImageById(repository.getImagesByEnId(id).get(0).getId());
        repository.deleteProductById(id);
        return "redirect:/admin/products";
    }


    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        //model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}
