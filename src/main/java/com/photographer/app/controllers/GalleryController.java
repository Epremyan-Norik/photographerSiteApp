package com.photographer.app.controllers;

import com.photographer.app.modelsNew.Album;
import com.photographer.app.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
class GalleryController {

    @Autowired
    Repository repository;

    @GetMapping("/albums")
    public String mainGallery(Model model) {
        model.addAttribute("title", "Галерея");
        List<Album> albums = repository.getAlbums();
        model.addAttribute("albums", albums);
        return "gallery";
    }

    @GetMapping("/albums/{id}")
    public String album(@PathVariable(value = "id") int id, Model model) {

        List<Album> albums = repository.getAlbums();
        for(Album album: albums){
            if(album.getId()==id){
                model.addAttribute("title", album.getName());
            }
        }

        return "album";
    }
}