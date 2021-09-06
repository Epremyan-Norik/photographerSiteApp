package com.photographer.app.controllers;


import com.photographer.app.mapper.BlogText;
import com.photographer.app.modelsNew.BlogPost;
import com.photographer.app.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private Repository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        //model.addAttribute("title", "Главная страница");
        Iterable<BlogPost> posts = postRepository.findAllBlogPost();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        BlogPost post = new BlogPost(title, anons, full_text, currentPrincipalName);
        postRepository.saveBlogPost(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String postView(@PathVariable(value = "id") int id, Model model) {
        if(!postRepository.blogPostExistsById(id)){
            return "redirect:/blog";
        }
        Optional<BlogPost> post = postRepository.findBlogPostById(id);
        ArrayList<BlogPost> res = new ArrayList<>();
        post.ifPresent(BlogPost::incrementView);
        post.ifPresent(System.out::println);
        post.ifPresent(res::add);
        post.ifPresent(postRepository::saveBlogPost);
        model.addAttribute("post", res);
        return "post-detail";
    }



    @GetMapping("/blog/{id}/edit")
    public String editPost(@PathVariable(value = "id") int id, Model model) {
        if(!postRepository.blogPostExistsById(id)){
            return "redirect:/blog";
        }
        Optional<BlogPost> post = postRepository.findBlogPostById(id);
        ArrayList<BlogPost> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") int id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text,
                                 Model model){
        BlogPost post = postRepository.findBlogPostById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(full_text);
        postRepository.saveBlogPost(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String removePost(@PathVariable(value = "id") int id, Model model) {
        BlogPost post = postRepository.findBlogPostById(id).orElseThrow();
        postRepository.saveBlogPost(post);
        return "redirect:/blog";
    }
}
