package com.photographer.app.controllers;

import com.photographer.app.model.*;
import com.photographer.app.repo.Repository;
import com.photographer.app.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            return "redirect:/admin/orders";
        }

        List<OrderItem> orderItems = repository.getAllOrderItemsByOrderID(order.getId());

        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
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
        Product product = repository.findProductById(id);
        if(product!=null){
            repository.deleteImageById(repository.getImagesByEnId(id).get(0).getId());
            repository.deleteProductById(id);
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/gallery")
    public String viewGallery(Model model) {
        model.addAttribute("title", "Редактирование галереи");
        List<Album> albums =  repository.getAlbums();
        model.addAttribute("albums", albums);
        return "admin-gallery";
    }

    @RequestMapping(value = "/admin/gallery/delete/{id}", method = RequestMethod.GET)
    public String deleteAlbum(@PathVariable(value = "id") long id) {
        repository.deleteAlbumById(id);
        return "redirect:/admin/gallery";
    }


    @RequestMapping(value = "/admin/gallery/add-album", method = RequestMethod.POST)
    public String addProduct(@RequestParam(name = "name") String name,
                             @RequestParam(name = "parent") long parent) {
        if(parent==0){
            parent = 54;
        }
        Album newAlbum = new Album();
        newAlbum.setP_id(parent);
        newAlbum.setName(name);
        repository.addAlbum(newAlbum);
        return "redirect:/admin/gallery";
    }

    @GetMapping("/admin/gallery/edit/{id}")
    public String editAlbum(@PathVariable(value="id") long id, Model model) {
        model.addAttribute("title", "Редактирование альбома");
        Album album =  repository.getAlbumById(id);
        List<Album> albums = repository.getAlbums();
        for(Album al: albums){
            if(al.getId()==album.getP_id()){
                albums.remove(al);
                break;
            }
        }
        List<Image> images = repository.getImagesByEnId(id);
        Album parent = repository.getAlbumById(album.getP_id());
        if(parent == null){
            parent = new Album();
            parent.setId(54);
            parent.setName("Корень");
        }
        model.addAttribute("album", album);
        model.addAttribute("albums", albums);
        model.addAttribute("parent", parent);
        model.addAttribute("images", images);



        return "admin-gallery-edit";
    }

    @RequestMapping(value = "/admin/gallery/edit-album{id}", method = RequestMethod.POST)
    public String addProduct(@PathVariable(value = "id") long id,
            @RequestParam(name = "name") String name,
                             @RequestParam(name = "parent") long parent) {
        if(parent==0){
            parent = 54;
        }
        Album newAlbum = new Album();
        newAlbum.setId(id);
        newAlbum.setP_id(parent);
        newAlbum.setName(name);
        repository.editAlbum(newAlbum);
        return "redirect:/admin/gallery/edit/"+String.valueOf(id);
    }

    @PostMapping("/admin/gallery/add-photo{id}")
    public String addPhotoToAlbum(@PathVariable(value = "id") long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return "redirect:/admin/gallery/edit/"+String.valueOf(id);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data:image/jpeg;base64,");
        stringBuilder.append(StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false)));

        Image newImage = new Image();
        newImage.setEn_id(id);
        newImage.setImagebyte64(stringBuilder.toString());
        newImage.setId(repository.insertImage(newImage));

        return "redirect:/admin/gallery/edit/"+String.valueOf(id);
    }

    @RequestMapping(value = "/admin/gallery/delete-photo/{a_id}/{im_id}", method = RequestMethod.GET)
    public String deleteImage(@PathVariable(value = "im_id") long im_id,
                              @PathVariable(value = "a_id") long a_id) {
        repository.deleteImageById(im_id);
        return "redirect:/admin/gallery/edit/"+String.valueOf(a_id);
    }


}
