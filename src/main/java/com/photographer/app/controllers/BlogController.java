package com.photographer.app.controllers;


import com.photographer.app.models.BlogPostOld;
import com.photographer.app.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private BlogPostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        //model.addAttribute("title", "Главная страница");
        Iterable<BlogPostOld> posts = postRepository.findAll();
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
        BlogPostOld post = new BlogPostOld(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String postView(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<BlogPostOld> post = postRepository.findById(id);
        ArrayList<BlogPostOld> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-detail";
    }



    @GetMapping("/blog/{id}/edit")
    public String editPost(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<BlogPostOld> post = postRepository.findById(id);
        ArrayList<BlogPostOld> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text,
                                 Model model){
        BlogPostOld post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String removePost(@PathVariable(value = "id") long id, Model model) {
        BlogPostOld post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}
