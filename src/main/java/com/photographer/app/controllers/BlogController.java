package com.photographer.app.controllers;


import com.photographer.app.models.BlogPost;
import com.photographer.app.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private BlogPostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        //model.addAttribute("title", "Главная страница");
        Iterable<BlogPost> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String addPost(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String postAdd(@RequestParam String title,
                          @RequestParam String anons,
                          @RequestParam String full_text,
                          Model model){
        BlogPost post = new BlogPost(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String postView(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<BlogPost> post = postRepository.findById(id);
        ArrayList<BlogPost> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-detail";
    }
}
